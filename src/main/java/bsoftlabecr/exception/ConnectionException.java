package bsoftlabecr.exception;

public class ConnectionException extends CashRegisterException {
    public ConnectionException(Integer responseCode) {
        super(responseCode);
        this.setResponseCode(responseCode);
    }
}
