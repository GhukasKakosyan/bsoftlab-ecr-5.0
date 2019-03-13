package bsoftlabecr.entity;

public enum ResponseCodes {

    OK(200);

    private Integer code;

    ResponseCodes(Integer code) {
        this.code = code;
    }
    public Integer getCode() {
        return this.code;
    }
}
