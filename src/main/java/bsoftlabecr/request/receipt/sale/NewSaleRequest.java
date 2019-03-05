package bsoftlabecr.request.receipt.sale;

import bsoftlabecr.entity.Item;
import bsoftlabecr.request.general.SequenceRequest;

import java.util.List;

public class NewSaleRequest extends SequenceRequest {
    private double paidAmount;          // Առձեռն վճարված գումար
    private double paidAmountCard;      // Անկանխիկ վճարված գումար
    private double partialAmount;       // Մասնակի վճարման գումար
    private double prePaymentAmount;    // Կանխավճարի օգտագործման գումար
    private int mode;                   // 2– Ապրանքներ ռեժիմ; 3- Կանխավճար
    private boolean useExtPOS;          // Այլ վճարային տերմինալի օգտագործում
    private List<Item> items;           // Կտրոնի ապրանքներ

    public double getPaidAmount() {
        return this.paidAmount;
    }
    public double getPaidAmountCard() {
        return this.paidAmountCard;
    }
    public double getPartialAmount() {
        return this.partialAmount;
    }
    public double getPrePaymentAmount() {
        return this.prePaymentAmount;
    }
    public int getMode() {
        return this.mode;
    }
    public boolean isUseExtPOS() {
        return this.useExtPOS;
    }
    public List<Item> getItems() {
        return this.items;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }
    public void setPaidAmountCard(double paidAmountCard) {
        this.paidAmountCard = paidAmountCard;
    }
    public void setPartialAmount(double partialAmount) {
        this.partialAmount = partialAmount;
    }
    public void setPrePaymentAmount(double prePaymentAmount) {
        this.prePaymentAmount = prePaymentAmount;
    }
    public void setMode(int mode) {
        this.mode = mode;
    }
    public void setUseExtPOS(boolean useExtPOS) {
        this.useExtPOS = useExtPOS;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
}
