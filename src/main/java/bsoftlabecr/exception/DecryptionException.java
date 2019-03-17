package bsoftlabecr.exception;

public class DecryptionException extends OperationException {
    public DecryptionException(Integer responseCode) {
        super(responseCode);
        this.setResponseCode(responseCode);
    }
}
