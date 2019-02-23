package BSOFTLABECR.request.receipt.prepayment;

import BSOFTLABECR.request.general.SequenceRequest;

public class ExistPrepaymentRequest extends SequenceRequest {
    private String crn;             // Վերադարձվող կտրոնի տպած սարքի գրանցման համար
    private String receiptId;       // Վերադարձվող կտրոնի համար

    public String getCrn () {
        return this.crn;
    }
    public String getReceiptId () {
        return this.receiptId;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }
    public void setReceiptId (String receiptId) {
        this.receiptId = receiptId;
    }
}
