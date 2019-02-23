package BSOFTLABECR.exception;

public class ECRException extends RuntimeException {
    public ECRException (int responseCode) {
        super("An error occurred. Error code: " + responseCode);
    }
}
