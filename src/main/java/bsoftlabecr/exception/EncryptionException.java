package bsoftlabecr.exception;

public class EncryptionException extends OperationException {
    public EncryptionException(Integer responseCode) {
        super(responseCode);
        this.setResponseCode(responseCode);
    }
}
