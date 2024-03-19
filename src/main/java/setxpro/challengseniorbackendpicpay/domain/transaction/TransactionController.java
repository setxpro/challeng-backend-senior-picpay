package setxpro.challengseniorbackendpicpay.domain.transaction;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.create(transaction);
    }

    @GetMapping
    public List<Transaction> getTransactions() {
        return transactionService.list();
    }
}
