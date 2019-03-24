package bsoftlabecr.request.cashier;

import bsoftlabecr.request.general.CashRegisterRequest;

public class LoginCashierRequest extends CashRegisterRequest {
    private Integer cashier = null;            // Օպերատորի կոդ
    private String password = null;            // ՀԴՄ գաղտնաբառը
    private String pin = null;                 // Օպերատորի գաղտնաբառ

    public Integer getCashier() {
        return this.cashier;
    }
    public String getPassword() {
        return this.password;
    }
    public String getPin() {
        return this.pin;
    }

    public void setCashier(Integer cashier) {
        this.cashier = cashier;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "[" +
                this.cashier + ", " +
                this.password + ", " +
                this.pin + "]";
    }
}
