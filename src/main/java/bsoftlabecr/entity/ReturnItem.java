package bsoftlabecr.entity;

public class ReturnItem {
    private long rpid;
    private double quantity;

    public long getRpid() {
        return this.rpid;
    }
    public double getQuantity() {
        return this.quantity;
    }

    public void setRpid(long rpid) {
        this.rpid = rpid;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
