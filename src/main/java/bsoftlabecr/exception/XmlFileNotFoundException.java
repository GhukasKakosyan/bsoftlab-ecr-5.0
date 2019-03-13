package bsoftlabecr.exception;

public class XmlFileNotFoundException extends Exception {
    public XmlFileNotFoundException(String fileName) {
        super(fileName + " file is not found.");
    }
}
