package bsoftlabecr.entity;

public class ReturnItem {
    private Long rpid;
    private Double quantity;

    public Long getRpid() {
        return this.rpid;
    }
    public Double getQuantity() {
        return this.quantity;
    }

    public void setRpid(Long rpid) {
        this.rpid = rpid;
    }
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
