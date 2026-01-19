package com.product_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "discounts")
public class DiscountEntity {

    @Id
    @Column(name = "discount_id")
    private String discountId;

    @Column(nullable = false)
    private BigDecimal percent;

    public DiscountEntity() {
    }

    public DiscountEntity(String discountId, BigDecimal percent) {
        this.discountId = discountId;
        this.percent = percent;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }
}
