package bsoftlabecr.response.cashier;

import java.io.IOException;

import sun.misc.BASE64Decoder;

public class LoginCashierResponse {
    private Integer responseCode = null;
    private String key = null;

    public Integer getResponseCode() {
        return this.responseCode;
    }
    public String getKey() {
        return this.key;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public byte[] getKeyBytes() {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            return decoder.decodeBuffer(this.key);
        } catch (IOException ioException) {
            return null;
        }
    }
}
