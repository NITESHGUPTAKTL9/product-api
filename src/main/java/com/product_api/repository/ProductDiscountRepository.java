package com.product_api.repository;

import com.product_api.model.ProductDiscountEntity;
import com.product_api.model.ProductDiscountId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDiscountRepository
        extends JpaRepository<ProductDiscountEntity, ProductDiscountId> {
    long countByIdProductIdAndIdDiscountId(String productId, String discountId);

}