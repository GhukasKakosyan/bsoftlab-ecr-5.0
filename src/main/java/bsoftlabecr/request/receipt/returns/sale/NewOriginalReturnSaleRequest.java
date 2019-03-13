package bsoftlabecr.request.receipt.returns.sale;

import bsoftlabecr.request.general.SequenceRequest;

public class NewOriginalReturnSaleRequest extends SequenceRequest {
    private Integer returnTicketId = null;          // Վերադարձվող կտրոնի համար
    private String crn = null;                      // Սարքի գրանցման համար

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
