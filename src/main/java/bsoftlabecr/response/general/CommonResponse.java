package bsoftlabecr.response.general;

public class CommonResponse {
    private Integer responseCode;
    public CommonResponse() {}
    public CommonResponse(Integer responseCode) {
        this.responseCode = responseCode;
    }
    public Integer getResponseCode() {
        return this.responseCode;
    }
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}
