package BSOFTLABECR.response.receipt.general;

import BSOFTLABECR.response.general.CommonResponse;

public class NewReceiptResponse extends CommonResponse {
    private int rseq;               // Կտրոնի հերթական համար
    private String crn;             // ՀԴՄ-ի գրանցման համարը
    private String sn;              // ՀԴՄ գործարանային համարը
    private String tin;             // Կազմակերպության ՀՎՀՀ-ն
    private String taxpayer;        // Կազմակերպության անվանումը
    private String address;         // Կազմակերպության հասցեն
    private double time;            // Կտրոնի գրանցման/տպման ամսաթիվ ու ժամ
    private String fiscal;          // Ֆիսկալ համար
    private String lottery;         // Վիճակահանության համարը
    private int prize;              // 0 – շահում չկա: 1 – շահում կա
    private double total;           // Ընդհանուր գումար
    private double change;          // Մանրադրամ
    private String qr;              // Անհրաժեշտ է տպել QR կոդի տեսքով

    public int getRseq() {
        return this.rseq;
    }
    public String getCrn() {
        return this.crn;
    }
    public String getSn() {
        return this.sn;
    }
    public String getTin() {
        return this.tin;
    }
    public String getTaxpayer() {
        return this.taxpayer;
    }
    public String getAddress() {
        return this.address;
    }
    public double getTime() {
        return this.time;
    }
    public String getFiscal() {
        return this.fiscal;
    }
    public String getLottery() {
        return this.lottery;
    }
    public int getPrize() {
        return this.prize;
    }
    public double getTotal() {
        return this.total;
    }
    public double getChange() {
        return this.change;
    }
    public String getQr() { return this.qr; }

    public void setRseq(int rseq) {
        this.rseq = rseq;
    }
    public void setCrn(String crn) {
        this.crn = crn;
    }
    public void setSn(String sn) {
        this.sn = sn;
    }
    public void setTin(String tin) {
        this.tin = tin;
    }
    public void setTaxpayer(String taxpayer) {
        this.taxpayer = taxpayer;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setTime(double time) {
        this.time = time;
    }
    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
    }
    public void setLottery(String lottery) {
        this.lottery = lottery;
    }
    public void setPrize(int prize) {
        this.prize = prize;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public void setChange(double change) {
        this.change = change;
    }
    public void setQr(String qr) { this.qr = qr; }
}
