package bsoftlabecr.exception;

public class XmlFileWriteException extends CashRegisterException {
    public XmlFileWriteException(Integer responseCode) {
        super(responseCode);
        this.setResponseCode(responseCode);
    }
}
