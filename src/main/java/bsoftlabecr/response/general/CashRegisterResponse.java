package bsoftlabecr.response.general;

public class CashRegisterResponse {
    private Integer responseCode = null;
    public CashRegisterResponse() {}
    public CashRegisterResponse(Integer responseCode) {
        this.responseCode = responseCode;
    }
    public Integer getResponseCode() {
        return this.responseCode;
    }
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}
