package setxpro.challengseniorbackendpicpay.infra.authorization;

public class UnauthorizedTransactionException extends RuntimeException {
    public UnauthorizedTransactionException(String message) {
        super(message);
    }
}
