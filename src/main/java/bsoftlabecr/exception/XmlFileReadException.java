package bsoftlabecr.exception;

public class XmlFileReadException extends CashRegisterException {
    public XmlFileReadException(Integer responseCode) {
        super(responseCode);
        this.setResponseCode(responseCode);
    }
}
