package bsoftlabecr.response.cashier;

import bsoftlabecr.entity.Cashier;
import bsoftlabecr.entity.Department;
import bsoftlabecr.response.general.CashRegisterResponse;

import java.util.List;

public class CashiersAndDepsResponse extends CashRegisterResponse {
    private List<Cashier> c = null;
    private List<Department> d = null;

    public List<Department> getD() {
        return this.d;
    }
    public List<Cashier> getC() {
        return this.c;
    }

    public void setD(List<Department> d) {
        this.d = d;
    }
    public void setC(List<Cashier> c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "[" +
                "responseCode: " + this.getResponseCode() + ", " +
                "cashiers: " + this.c.toString() + ", " +
                "departments: " + this.d.toString() + "]";

    }
}
