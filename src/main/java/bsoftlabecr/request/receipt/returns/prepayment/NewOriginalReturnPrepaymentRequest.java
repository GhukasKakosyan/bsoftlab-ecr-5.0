package bsoftlabecr.request.receipt.returns.prepayment;

import bsoftlabecr.request.general.SequenceRequest;

public class NewOriginalReturnPrepaymentRequest extends SequenceRequest {
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
}