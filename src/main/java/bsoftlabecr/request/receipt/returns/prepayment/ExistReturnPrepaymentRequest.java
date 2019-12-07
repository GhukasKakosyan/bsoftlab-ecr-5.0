package bsoftlabecr.request.receipt.returns.prepayment;

import bsoftlabecr.request.general.SequencedCashRegisterRequest;

public class ExistReturnPrepaymentRequest extends SequencedCashRegisterRequest {
    private String crn = null;             // Վերադարձվող կտրոնի տպած սարքի գրանցման համար
    private String receiptId = null;       // Վերադարձվող կտրոնի համար

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

    @Override
    public String toString() {
        return "[" +
                "seq: " + this.getSeq() +
                "crn: " + this.crn + ", " +
                "receiptId: " + this.receiptId + "]";
    }
}
