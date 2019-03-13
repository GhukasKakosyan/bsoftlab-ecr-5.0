package bsoftlabecr.exception;

public class XmlFileInvalidException extends Exception {
    public XmlFileInvalidException(String fileName) {
        super(fileName + " file has invalid structure.");
    }
}
