package com.product_api.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "product_discounts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_id", "discount_id"})
        }
)
public class ProductDiscountEntity {

    @EmbeddedId
    private ProductDiscountId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("discountId")
    @JoinColumn(name = "discount_id", nullable = false)
    private DiscountEntity discount;

    @Column(name = "applied_at", nullable = false)
    private Instant appliedAt;

    protected ProductDiscountEntity() {
    }

    public ProductDiscountEntity(ProductEntity product, DiscountEntity discount) {
        this.product = product;
        this.discount = discount;
        this.id = new ProductDiscountId(product.getId(), discount.getDiscountId());
        this.appliedAt = Instant.now();
    }

    public ProductDiscountId getId() {
        return id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public DiscountEntity getDiscount() {
        return discount;
    }

    public Instant getAppliedAt() {
        return appliedAt;
    }
}
