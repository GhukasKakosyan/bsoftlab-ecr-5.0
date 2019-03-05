package bsoftlabecr.request.receipt.returns.prepayment;

import bsoftlabecr.request.general.SequenceRequest;

public class NewORPrepaymentRequest extends SequenceRequest {
    private int returnTicketId;         // Վերադարձվող կտրոնի համար
    private String crn;                 // Սարքի գրանցման համար

    public int getReturnTicketId() {
        return this.returnTicketId;
    }
    public String getCrn() {
        return this.crn;
    }

    public void setReturnTicketId(int returnTicketId) {
        this.returnTicketId = returnTicketId;
    }
    public void setCrn(String crn) {
        this.crn = crn;
    }
}
