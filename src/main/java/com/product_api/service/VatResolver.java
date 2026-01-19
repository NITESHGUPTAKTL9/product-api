package com.product_api.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class VatResolver {

    private static final Map<String, BigDecimal> VAT = Map.of(
            "Sweden", BigDecimal.valueOf(0.25),
            "Germany", BigDecimal.valueOf(0.19),
            "France", BigDecimal.valueOf(0.20)
    );

    public BigDecimal vatFor(String country) {
        return VAT.getOrDefault(country, BigDecimal.ZERO);
    }
}
