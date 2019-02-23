package BSOFTLABECR.request.receipt.returns.sale;

import BSOFTLABECR.request.general.SequenceRequest;

public class NewORSaleRequest extends SequenceRequest {
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
