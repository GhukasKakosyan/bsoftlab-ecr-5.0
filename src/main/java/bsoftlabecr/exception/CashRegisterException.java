package bsoftlabecr.exception;

public class CashRegisterException extends RuntimeException {
    public CashRegisterException(int responseCode) {
        super("An error occurred. Error code: " + responseCode);
    }
}
