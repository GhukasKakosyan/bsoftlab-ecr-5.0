package bsoftlabecr.request.cashier;

import bsoftlabecr.request.general.CashRegisterRequest;

public class CashiersAndDepsRequest extends CashRegisterRequest {
    private String password;

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
