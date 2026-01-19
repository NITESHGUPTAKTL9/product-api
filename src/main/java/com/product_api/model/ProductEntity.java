package com.product_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Column(nullable = false)
    private String country;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductDiscountEntity> discounts = new HashSet<>();

    protected ProductEntity() {}

    public ProductEntity(String id, String name, BigDecimal basePrice, String country) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<ProductDiscountEntity> getDiscounts() {
        return discounts;
    }
}
