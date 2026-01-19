package com.product_api.service;

import com.product_api.model.DiscountEntity;
import com.product_api.model.ProductDiscountEntity;
import com.product_api.model.ProductEntity;
import com.product_api.repository.DiscountRepository;
import com.product_api.repository.ProductDiscountRepository;
import com.product_api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountService {

    private final ProductRepository productRepo;
    private final DiscountRepository discountRepo;
    private final ProductDiscountRepository pdRepo;

    public DiscountService(ProductRepository productRepo,
                           DiscountRepository discountRepo,
                           ProductDiscountRepository pdRepo) {
        this.productRepo = productRepo;
        this.discountRepo = discountRepo;
        this.pdRepo = pdRepo;
    }

    @Transactional
    public void applyDiscount(String productId, String discountId, BigDecimal percent) {

        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        DiscountEntity discount = discountRepo.findById(discountId)
                .orElseGet(() -> {
                    DiscountEntity d = new DiscountEntity(discountId, percent);
                    return discountRepo.save(d);
                });

        ProductDiscountEntity pd = new ProductDiscountEntity(product, discount);

        try {
            pdRepo.save(pd);
        } catch (DataIntegrityViolationException e) {
            // Idempotent behavior: discount already applied
        }
    }
}
