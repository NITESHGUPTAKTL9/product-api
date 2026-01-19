package com.product_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductDiscountId implements Serializable {

    @Column(name = "product_id")
    private String productId;

    @Column(name = "discount_id")
    private String discountId;

    protected ProductDiscountId() {}

    public ProductDiscountId(String productId, String discountId) {
        this.productId = productId;
        this.discountId = discountId;
    }

    public String getProductId() {
        return productId;
    }

    public String getDiscountId() {
        return discountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDiscountId that)) return false;
        return Objects.equals(productId, that.productId)
                && Objects.equals(discountId, that.discountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, discountId);
    }
}
