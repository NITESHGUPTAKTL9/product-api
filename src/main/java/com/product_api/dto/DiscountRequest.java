package com.product_api.dto;

import java.math.BigDecimal;

public record DiscountRequest(String discountId, BigDecimal percent) {}