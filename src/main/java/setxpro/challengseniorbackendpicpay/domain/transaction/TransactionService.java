package setxpro.challengseniorbackendpicpay.domain.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import setxpro.challengseniorbackendpicpay.domain.notification.NotificationService;
import setxpro.challengseniorbackendpicpay.domain.wallet.Wallet;
import setxpro.challengseniorbackendpicpay.domain.wallet.WalletRepository;
import setxpro.challengseniorbackendpicpay.domain.wallet.WalletType;
import setxpro.challengseniorbackendpicpay.infra.authorization.AuthorizationService;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final  WalletRepository walletRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository, AuthorizationService authorizationService, NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
    }

    /***
     * if one operation fail
     * will be make rollback of the all operations
     * that went make in database
     */
    @Transactional
    public Transaction create(Transaction transaction) {
        // 1 - Validate
        validate(transaction);

        // 2 - Create a transaction
        var newTransaction = this.transactionRepository.save(transaction);

        // 3 - debit from waller
        // Find waller associated with the payer

        // Debit
        var walletPayer = walletRepository.findById(transaction.payer()).get();
        walletRepository.save(walletPayer.debit(transaction.value()));

        // Credit
        var walletPayee = walletRepository.findById(transaction.payee()).get();
        walletRepository.save(walletPayee.credit(transaction.value()));


        // 4 - to call external services

        // Authorize transaction
        authorizationService.authorize(transaction);

        // notification transaction
        notificationService.notify(transaction);

        return newTransaction;
    }

    /**
     * @param transaction - to validate
     * - the payer has a common wallet
     * - the payer has enough balance
     * - the payer is not the payee
     */
    private void validate(Transaction transaction) {
        walletRepository.findById(transaction.payee())
                .map(payee -> walletRepository.findById(transaction.payer())
                        .map(payer -> isTransactionValid(transaction, payer) ? transaction : null)
                        .orElseThrow(() -> new InvalidTransactionException("Invalid transaction First - %s".formatted(transaction)))) // invalid transaction
                .orElseThrow(() -> new InvalidTransactionException("Invalid transaction - Secondary %s".formatted(transaction))); // Payer not found - invalid id
    }
    private static boolean isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.COMMON.getValue() &&
                payer.balance().compareTo(transaction.value()) >= 0 &&
                !payer.id().equals(transaction.payee());
    }

    public List<Transaction> list() {
        return transactionRepository.findAll();
    }
}
