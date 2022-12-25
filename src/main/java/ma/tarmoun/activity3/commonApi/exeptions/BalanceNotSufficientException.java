package ma.tarmoun.activity3.commonApi.exeptions;

public class BalanceNotSufficientException extends RuntimeException {
    public BalanceNotSufficientException(String balance_not_sufficitient) {
        super(balance_not_sufficitient);

    }
}
