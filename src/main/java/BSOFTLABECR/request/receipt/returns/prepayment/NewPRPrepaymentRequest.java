package BSOFTLABECR.request.receipt.returns.prepayment;

import BSOFTLABECR.request.general.SequenceRequest;

import java.math.BigDecimal;

public class NewPRPrepaymentRequest extends SequenceRequest {
    private int returnTicketId;                 // Վերադարձվող կտրոնի համար
    private String crn;                         // Սարքի գրանցման համար
    private BigDecimal cashAmountForReturn;     // Առձեռն վերադարձվող գումար: Տվյալը ուղարկվում է, եթե կատարվել է մասնակի վերադարձ
    private BigDecimal cardAmountForReturn;     // Անկանխիկ վերադարձվող գումար: Տվյալը ուղարկվում է, եթե կատարվել է մասնակի վերադարձ

    public int getReturnTicketId() {
        return this.returnTicketId;
    }
    public String getCrn() {
        return this.crn;
    }
    public BigDecimal getCashAmountForReturn() {
        return this.cashAmountForReturn;
    }
    public BigDecimal getCardAmountForReturn() {
        return cardAmountForReturn;
    }

    public void setReturnTicketId(int returnTicketId) {
        this.returnTicketId = returnTicketId;
    }
    public void setCrn(String crn) {
        this.crn = crn;
    }
    public void setCashAmountForReturn(BigDecimal cashAmountForReturn) {
        this.cashAmountForReturn = cashAmountForReturn;
    }
    public void setCardAmountForReturn(BigDecimal cardAmountForReturn) {
        this.cardAmountForReturn = cardAmountForReturn;
    }
}
