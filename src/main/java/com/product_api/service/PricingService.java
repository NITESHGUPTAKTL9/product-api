package com.product_api.service;

import com.product_api.model.ProductEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PricingService {

    private final VatResolver vatResolver;

    public PricingService(VatResolver vatResolver) {
        this.vatResolver = vatResolver;
    }

    public BigDecimal finalPrice(ProductEntity product) {

        BigDecimal totalDiscount = product.getDiscounts().stream()
                .map(pd -> pd.getDiscount().getPercent())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(100));

        BigDecimal vat = vatResolver.vatFor(product.getCountry());

        return product.getBasePrice()
                .multiply(BigDecimal.ONE.subtract(totalDiscount))
                .multiply(BigDecimal.ONE.add(vat))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
