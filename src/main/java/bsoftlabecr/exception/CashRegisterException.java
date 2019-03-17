package bsoftlabecr.exception;

public class CashRegisterException extends Exception {
    private Integer responseCode;
    public CashRegisterException(Integer responseCode) {
        super(responseCode.toString());
        this.responseCode = responseCode;
    }
    public Integer getResponseCode() {
        return this.responseCode;
    }
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}
