package bsoftlabecr.request.receipt.returns.sale;

import bsoftlabecr.request.general.SequencedCashRegisterRequest;

public class ExistReturnSaleRequest extends SequencedCashRegisterRequest {
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
}
