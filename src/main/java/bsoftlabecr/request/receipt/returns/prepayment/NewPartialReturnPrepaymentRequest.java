package bsoftlabecr.request.receipt.returns.prepayment;

import bsoftlabecr.request.general.SequenceRequest;

import java.math.BigDecimal;

public class NewPartialReturnPrepaymentRequest extends SequenceRequest {
    private Integer returnTicketId = null;             // Վերադարձվող կտրոնի համար
    private String crn = null;                         // Սարքի գրանցման համար
    private BigDecimal cashAmountForReturn = null;     // Առձեռն վերադարձվող գումար: Տվյալը ուղարկվում է, եթե կատարվել է մասնակի վերադարձ
    private BigDecimal cardAmountForReturn = null;     // Անկանխիկ վերադարձվող գումար: Տվյալը ուղարկվում է, եթե կատարվել է մասնակի վերադարձ

    public Integer getReturnTicketId() {
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

    public void setReturnTicketId(Integer returnTicketId) {
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
