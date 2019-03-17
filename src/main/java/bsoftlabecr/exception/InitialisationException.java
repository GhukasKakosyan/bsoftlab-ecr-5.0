package bsoftlabecr.exception;

public class InitialisationException extends CashRegisterException {
    public InitialisationException(Integer responseCode) {
        super(responseCode);
        this.setResponseCode(responseCode);
    }
}
