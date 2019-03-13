package bsoftlabecr.exception;

import bsoftlabecr.response.general.CommonResponse;

public class CommonResponseException extends Exception {
    private CommonResponse commonResponse;
    public CommonResponseException(CommonResponse commonResponse) {
        super("Error is occurred. Response code = " + commonResponse);
        this.commonResponse = commonResponse;
    }
    public CommonResponse getCommonResponse() {
        return this.commonResponse;
    }
    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }
}
