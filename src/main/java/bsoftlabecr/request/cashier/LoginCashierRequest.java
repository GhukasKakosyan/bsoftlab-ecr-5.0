package bsoftlabecr.request.cashier;

import bsoftlabecr.request.general.CommonRequest;

public class LoginCashierRequest extends CommonRequest {
    private int cashier;            // Օպերատորի կոդ
    private String password;        // ՀԴՄ գաղտնաբառը
    private String pin;             // Օպերատորի գաղտնաբառ

    public int getCashier() {
        return this.cashier;
    }
    public String getPassword() {
        return this.password;
    }
    public String getPin() {
        return this.pin;
    }

    public void setCashier(int cashier) {
        this.cashier = cashier;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }
}
