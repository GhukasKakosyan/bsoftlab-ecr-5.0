package bsoftlabecr.entity;

import java.math.BigDecimal;

public class Item {
    private Integer dep = null;
    private String adgCode = null;
    private String productCode = null;
    private String productName = null;
    private String unit = null;
    private BigDecimal qty = null;
    private BigDecimal price = null;
    private BigDecimal discount = null;
    private Integer discountType = null;
    private BigDecimal additionalDiscount = null;
    private Integer additionalDiscountType = null;

    public Integer getDep() {
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

    public void setDep(Integer dep) {
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