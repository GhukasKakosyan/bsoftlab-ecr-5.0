package bsoftlabecr.response.cashier;

import bsoftlabecr.entity.Cashier;
import bsoftlabecr.entity.Department;

import java.util.List;

public class CashiersAndDepsResponse {
    private Integer responseCode = null;
    private List<Cashier> c = null;
    private List<Department> d = null;

    public Integer getResponseCode() {
        return this.responseCode;
    }
    public List<Department> getD() {
        return this.d;
    }
    public void setD(List<Department> d) {
        this.d = d;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
    public List<Cashier> getC() {
        return this.c;
    }
    public void setC(List<Cashier> c) {
        this.c = c;
    }
}
