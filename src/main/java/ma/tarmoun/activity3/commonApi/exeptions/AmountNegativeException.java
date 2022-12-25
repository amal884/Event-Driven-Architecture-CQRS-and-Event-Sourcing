package ma.tarmoun.activity3.commonApi.exeptions;

public class AmountNegativeException extends RuntimeException {
    public AmountNegativeException(String message) {
        super(message);
    }
}
