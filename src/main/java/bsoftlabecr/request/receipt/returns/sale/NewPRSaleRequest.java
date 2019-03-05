package bsoftlabecr.request.receipt.returns.sale;

import bsoftlabecr.entity.ReturnItem;
import bsoftlabecr.request.general.SequenceRequest;

import java.math.BigDecimal;
import java.util.List;

public class NewPRSaleRequest extends SequenceRequest {
    private int returnTicketId;                 // Վերադարձվող կտրոնի համար
    private String crn;                         // Սարքի գրանցման համար
    private BigDecimal cashAmountForReturn;     // Առձեռն վերադարձվող գումար: Տվյալը ուղարկվում է, եթե կատարվել է մասնակի վերադարձ
    private BigDecimal cardAmountForReturn;     // Անկանխիկ վերադարձվող գումար: Տվյալը ուղարկվում է, եթե կատարվել է մասնակի վերադարձ
    private List<ReturnItem> returnItemList;    // Վերադարձվող ապրանքների տվյալներ: Տվյալը ուղարկվում է միայն, եթե տեղի է ունեցել ապրանքներով կտրոնի մասնակի վերադարձ

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
    public List<ReturnItem> getReturnItemList() {
        return this.returnItemList;
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
    public void setReturnItemList(List<ReturnItem> returnItemList) {
        this.returnItemList = returnItemList;
    }

}
