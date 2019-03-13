package bsoftlabecr.response.receipt.general;

import bsoftlabecr.response.general.CommonResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties({"error"})
public class ExistReceiptResponse extends CommonResponse {
    private String cid = null;         // Գանձապահի ID
    private String time = null;        // Կտրոնի գրանցման/տպման ամսաթիվ ու ժամ
    private String ta = null;          // Ընդհամենը գումար
    private String cash = null;        // Առձեռն մուծված գումար
    private String card = null;        // Անկանխիկ մուծված գումար
    private String ppa = null;         // Մասնակի վճարված գումար
    private String ppu = null;         // Օգտագործված կանխավճար
    private String ref = null;         // Վերադարդարձվող կտրոնի համար
    private String refcrn = null;      // Վերադարձվող կտրոն տպած ՀԴՄ-ի գրանցման համար
    private String saleType = null;    // Գործարքի տիպ՝ 0 – Վաճառք, 2 – Վերադարձ, 3 – Կանխավճար
    private String type = null;        // Գործարքի տիպ՝ 0 – Վաճառք, 2 – Վերադարձ, 3 – Կանխավճար

    public ExistReceiptResponse() {}

    public String getCid() {
        return this.cid;
    }
    public String getTime() {
        return this.time;
    }
    public String getTa() {
        return this.ta;
    }
    public String getCash() {
        return this.cash;
    }
    public String getCard() {
        return this.card;
    }
    public String getPpa() {
        return this.ppa;
    }
    public String getPpu() {
        return this.ppu;
    }
    public String getRef() {
        return this.ref;
    }
    public String getRefcrn() {
        return this.refcrn;
    }
    public String getSaleType() {
        return this.saleType;
    }
    public String getType() {
        return this.type;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setTa(String ta) {
        this.ta = ta;
    }
    public void setCash(String cash) {
        this.cash = cash;
    }
    public void setCard(String card) {
        this.card = card;
    }
    public void setPpa(String ppa) {
        this.ppa = ppa;
    }
    public void setPpu(String ppu) {
        this.ppu = ppu;
    }
    public void setRef(String ref) {
        this.ref = ref;
    }
    public void setRefcrn(String refcrn) {
        this.refcrn = refcrn;
    }
    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }
    public void setType(String type) {
        this.type = type;
    }

    @JsonDeserialize(as = SubTotal[].class)
    @JsonUnwrapped
    public SubTotal[] totals;

    public static class SubTotal {
        private String did = null;     //Բաժնի համար
        private String dt = null;      //Բաժնի ԱԱՀ
        private String dtm = null;     //Բաժնի հարկման ռեժիմ
        private String t = null;       //Ընդամենը գումար առանց ԱԱՀ
        private String tt = null;      //Ընդամենը գումար ԱԱՀ-ով
        private String gc = null;      //Ապրանքի կոդ
        private String gn = null;      //Ապրանքի անվանում
        private String qty = null;     //Ապրանքի քանակ
        private String p = null;       //Ապրանքի գին
        private String adg = null;     //Ապրանքի ԱՏԳ կոդ
        private String mu = null;      //Ապրանքի չափման միավոր
        private String dsc = null;     //Զեղչ
        private String adsc = null;    //Համամասնորեն զեղչ
        private String dsct = null;    //Զեղչի տեսակ
        private String rpid = null;    //Ապրանքի տողի համարը

        public SubTotal() {}

        public String getDid() {
            return this.did;
        }
        public String getDt() {
            return this.dt;
        }
        public String getDtm() {
            return this.dtm;
        }
        public String getT() {
            return this.t;
        }
        public String getTt() {
            return this.tt;
        }
        public String getGc() {
            return this.gc;
        }
        public String getGn() {
            return this.gn;
        }
        public String getQty() {
            return this.qty;
        }
        public String getP() {
            return this.p;
        }
        public String getAdg() {
            return this.adg;
        }
        public String getMu() {
            return this.mu;
        }
        public String getDsc() {
            return this.dsc;
        }
        public String getAdsc() {
            return this.adsc;
        }
        public String getDsct() {
            return this.dsct;
        }
        public String getRpid() {
            return this.rpid;
        }

        public void setDid(String did) {
            this.did = did;
        }
        public void setDt(String dt) {
            this.dt = dt;
        }
        public void setDtm(String dtm) {
            this.dtm = dtm;
        }
        public void setT(String t) {
            this.t = t;
        }
        public void setTt(String tt) {
            this.tt = tt;
        }
        public void setGc(String gc) {
            this.gc = gc;
        }
        public void setGn(String gn) {
            this.gn = gn;
        }
        public void setQty(String qty) {
            this.qty = qty;
        }
        public void setP(String p) {
            this.p = p;
        }
        public void setAdg(String adg) {
            this.adg = adg;
        }
        public void setMu(String mu) {
            this.mu = mu;
        }
        public void setDsc(String dsc) {
            this.dsc = dsc;
        }
        public void setAdsc(String adsc) {
            this.adsc = adsc;
        }
        public void setDsct(String dsct) {
            this.dsct = dsct;
        }
        public void setRpid(String rpid) {
            this.rpid = rpid;
        }
    }
}