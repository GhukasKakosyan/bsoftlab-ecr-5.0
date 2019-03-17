package bsoftlabecr.exception;

public class NetworkException extends OperationException {
    public NetworkException(Integer responseCode) {
        super(responseCode);
        this.setResponseCode(responseCode);
    }
}
