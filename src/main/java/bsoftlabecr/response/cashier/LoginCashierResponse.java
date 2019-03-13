package bsoftlabecr.response.cashier;

import java.io.IOException;

import bsoftlabecr.response.general.CommonResponse;
import sun.misc.BASE64Decoder;

public class LoginCashierResponse extends CommonResponse {
    private String key;

    public String getKey() {
        return this.key;
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
