package bsoftlabecr.request.receipt.returns.prepayment;

import bsoftlabecr.request.general.SequencedCashRegisterRequest;

public class NewOriginalReturnPrepaymentRequest extends SequencedCashRegisterRequest {
    private Integer returnTicketId = null;         // Վերադարձվող կտրոնի համար
    private String crn = null;                     // Սարքի գրանցման համար

    public Integer getReturnTicketId() {
        return this.returnTicketId;
    }
    public String getCrn() {
        return this.crn;
    }

    public void setReturnTicketId(Integer returnTicketId) {
        this.returnTicketId = returnTicketId;
    }
    public void setCrn(String crn) {
        this.crn = crn;
    }

    @Override
    public String toString() {
        return "[" +
                "seq: " + this.getSeq() + ", " +
                "returnTicketId: " + this.returnTicketId + ", " +
                "crn: " + this.crn + "]";
    }
}
