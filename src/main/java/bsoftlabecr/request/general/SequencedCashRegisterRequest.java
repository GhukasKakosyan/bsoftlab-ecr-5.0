package bsoftlabecr.request.general;

public class SequencedCashRegisterRequest extends CashRegisterRequest {
    private Integer seq = null;

    public Integer getSeq() {
        return this.seq;
    }
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "[" + this.seq + "]";
    }
}
