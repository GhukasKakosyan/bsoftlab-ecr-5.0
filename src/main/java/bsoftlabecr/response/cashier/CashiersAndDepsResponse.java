package bsoftlabecr.response.cashier;

import bsoftlabecr.entity.Cashier;
import bsoftlabecr.entity.Department;
import bsoftlabecr.response.general.CommonResponse;

import java.util.List;

public class CashiersAndDepsResponse extends CommonResponse {
    private List<Cashier> c;
    private List<Department> d;

    public List<Department> getD() {
        return this.d;
    }
    public void setD(List<Department> d) {
        this.d = d;
    }

    public List<Cashier> getC() {
        return this.c;
    }
    public void setC(List<Cashier> c) {
        this.c = c;
    }
}