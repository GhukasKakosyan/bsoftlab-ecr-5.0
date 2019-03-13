package bsoftlabecr.response.receipt.general;

import bsoftlabecr.response.general.CommonResponse;

public class NewReceiptResponse extends CommonResponse {
    private Integer rseq = null;           // Կտրոնի հերթական համար
    private String crn = null;             // ՀԴՄ-ի գրանցման համարը
    private String sn = null;              // ՀԴՄ գործարանային համարը
    private String tin = null;             // Կազմակերպության ՀՎՀՀ-ն
    private String taxpayer = null;        // Կազմակերպության անվանումը
    private String address = null;         // Կազմակերպության հասցեն
    private Double time = null;            // Կտրոնի գրանցման/տպման ամսաթիվ ու ժամ
    private String fiscal = null;          // Ֆիսկալ համար
    private String lottery = null;         // Վիճակահանության համարը
    private Integer prize = null;          // 0 – շահում չկա: 1 – շահում կա
    private Double total = null;           // Ընդհանուր գումար
    private Double change = null;          // Մանրադրամ
    private String qr = null;              // Անհրաժեշտ է տպել QR կոդի տեսքով

    public Integer getRseq() {
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
    public Double getTime() {
        return this.time;
    }
    public String getFiscal() {
        return this.fiscal;
    }
    public String getLottery() {
        return this.lottery;
    }
    public Integer getPrize() {
        return this.prize;
    }
    public Double getTotal() {
        return this.total;
    }
    public Double getChange() {
        return this.change;
    }
    public String getQr() { return this.qr; }

    public void setRseq(Integer rseq) {
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
    public void setTime(Double time) {
        this.time = time;
    }
    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
    }
    public void setLottery(String lottery) {
        this.lottery = lottery;
    }
    public void setPrize(Integer prize) {
        this.prize = prize;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
    public void setChange(Double change) {
        this.change = change;
    }
    public void setQr(String qr) { this.qr = qr; }
}