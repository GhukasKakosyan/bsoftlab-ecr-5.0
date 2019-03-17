package bsoftlabecr.exception;

public class OperationException extends CashRegisterException {
    public OperationException(Integer responseCode) {
        super(responseCode);
        this.setResponseCode(responseCode);
    }
}
