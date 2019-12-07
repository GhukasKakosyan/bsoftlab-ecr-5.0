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
    private BigDecimal discountType = null;
    private BigDecimal additionalDiscount = null;
    private BigDecimal additionalDiscountType = null;

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
    public BigDecimal getDiscountType() {
        return this.discountType;
    }
    public BigDecimal getAdditionalDiscount() {
        return this.additionalDiscount;
    }
    public BigDecimal getAdditionalDiscountType() {
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
    public void setDiscountType(BigDecimal discountType) {
        this.discountType = discountType;
    }
    public void setAdditionalDiscount(BigDecimal additionalDiscount) {
        this.additionalDiscount = additionalDiscount;
    }
    public void setAdditionalDiscountType(BigDecimal additionalDiscountType) {
        this.additionalDiscountType = additionalDiscountType;
    }

    @Override
    public String toString() {
        return "[" +
                "dep: " + this.dep + ", " +
                "adgCode: " + this.adgCode + ", " +
                "productCode: " + this.productCode + ", " +
                "productName: " + this.productName + ", " +
                "unit: " + this.unit + ", " +
                "qty: " + this.qty + ", " +
                "price: " + this.price + ", " +
                "discount: " + this.discount + ", " +
                "discountType: " + this.discountType + ", " +
                "additionalDiscount: " + this.additionalDiscount + ", " +
                "additionalDiscountType: " + this.additionalDiscountType + "]";
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