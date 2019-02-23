package BSOFTLABECR.request.cashier;

import BSOFTLABECR.request.general.CommonRequest;

public class CashiersAndDepsRequest extends CommonRequest{
    private String password;

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
