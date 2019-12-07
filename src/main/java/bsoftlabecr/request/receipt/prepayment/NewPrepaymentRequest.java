package bsoftlabecr.request.receipt.prepayment;

import bsoftlabecr.entity.Item;
import bsoftlabecr.request.general.SequencedCashRegisterRequest;

import java.util.List;

public class NewPrepaymentRequest extends SequencedCashRegisterRequest {
    private String partnerTin = null;          // Գնորդի ՀՎՀՀ
    private Double paidAmount = null;          // Առձեռն վճարված գումար
    private Double paidAmountCard = null;      // Անկանխիկ վճարված գումար
    private Double partialAmount = null;       // Մասնակի վճարման գումար (Ներկայումս պարտադիր պետք է լինի 0)
    private Double prePaymentAmount = null;    // Կանխավճարի օգտագործման գումար (Ներկայումս պարտադիր պետք է լինի 0)
    private Integer mode = null;               // 2– Ապրանքներ ռեժիմ; 3- Կանխավճար
    private Boolean useExtPOS = null;          // Այլ վճարային տերմինալի օգտագործում
    private List<Item> items = null;           // Կտրոնի ապրանքներ

    public String getPartnerTin() {
        return this.partnerTin;
    }
    public Double getPaidAmount() {
        return this.paidAmount;
    }
    public Double getPaidAmountCard() {
        return this.paidAmountCard;
    }
    public Double getPartialAmount() {
        return this.partialAmount;
    }
    public Double getPrePaymentAmount() {
        return this.prePaymentAmount;
    }
    public Integer getMode() {
        return this.mode;
    }
    public Boolean isUseExtPOS() {
        return this.useExtPOS;
    }
    public List<Item> getItems() {
        return this.items;
    }

    public void setPartnerTin(String partnerTin) {
        this.partnerTin = partnerTin;
    }
    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }
    public void setPaidAmountCard(Double paidAmountCard) {
        this.paidAmountCard = paidAmountCard;
    }
    public void setPartialAmount(Double partialAmount) {
        this.partialAmount = partialAmount;
    }
    public void setPrePaymentAmount(Double prePaymentAmount) {
        this.prePaymentAmount = prePaymentAmount;
    }
    public void setMode(Integer mode) {
        this.mode = mode;
    }
    public void setUseExtPOS(Boolean useExtPOS) {
        this.useExtPOS = useExtPOS;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "[" +
                "seq: " + this.getSeq() + ", " +
                "partnerTin: " + this.partnerTin + ", " +
                "paidAmount: " + this.paidAmount + ", " +
                "paidAmountCard: " + this.paidAmountCard + ", " +
                "partialAmount: " + this.partialAmount + ", " +
                "prePaymentAmount: " + this.prePaymentAmount + ", " +
                "mode: " + this.mode + ", " +
                "useExtPOS: " + this.useExtPOS + ", " +
                "items: " + this.items.toString() + "]";
    }
}