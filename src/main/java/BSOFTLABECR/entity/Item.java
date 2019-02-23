package BSOFTLABECR.entity;

import java.math.BigDecimal;

public class Item {
    private int dep;
    private String adgCode;
    private String productCode;
    private String productName;
    private String unit;
    private BigDecimal qty;
    private BigDecimal price;
    private BigDecimal discount;
    private Integer discountType;
    private BigDecimal additionalDiscount;
    private Integer additionalDiscountType;

    public int getDep() {
        return this.dep;
    }
    public String getAdgCode() {
        return this.adgCode;
    }
    public String getProductCode() {
        return this.productCode;
    }
    public String getProductName() {
        return this.productName;
    }
    public String getUnit() {
        return this.unit;
    }
    public BigDecimal getQty() {
        return this.qty;
    }
    public BigDecimal getPrice() {
        return this.price;
    }
    public BigDecimal getDiscount() {
        return this.discount;
    }
    public Integer getDiscountType() {
        return this.discountType;
    }
    public BigDecimal getAdditionalDiscount() {
        return this.additionalDiscount;
    }
    public Integer getAdditionalDiscountType() {
        return this.additionalDiscountType;
    }

    public void setDep(int dep) {
        this.dep = dep;
    }
    public void setAdgCode(String adgCode) {
        this.adgCode = adgCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }
    public void setAdditionalDiscount(BigDecimal additionalDiscount) {
        this.additionalDiscount = additionalDiscount;
    }
    public void setAdditionalDiscountType(Integer additionalDiscountType) {
        this.additionalDiscountType = additionalDiscountType;
    }

    public enum ProductDiscountType {
        PERCENT(1),
        PRICE(2),
        TOTAL(4);

        private Integer value;

        ProductDiscountType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
        public void setValue (Integer value) {
            this.value = value;
        }
    }

    public enum ProductAdditionalDiscountType {
        PERCENT(8),
        PRICE(16);

        private Integer value;

        ProductAdditionalDiscountType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
        public void setValue (Integer value) {
            this.value = value;
        }
    }
}