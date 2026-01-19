package com.product_api.controller;

import com.product_api.dto.DiscountRequest;
import com.product_api.dto.ProductResponse;
import com.product_api.repository.ProductRepository;
import com.product_api.service.DiscountService;
import com.product_api.service.PricingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepo;
    private final PricingService pricingService;
    private final DiscountService discountService;

    public ProductController(ProductRepository productRepo,
                             PricingService pricingService,
                             DiscountService discountService) {
        this.productRepo = productRepo;
        this.pricingService = pricingService;
        this.discountService = discountService;
    }

    @PutMapping("/{id}/discount")
    public ResponseEntity<Void> applyDiscount(
            @PathVariable String id,
            @RequestBody DiscountRequest req) {

        discountService.applyDiscount(id, req.discountId(), req.percent());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<ProductResponse> products(@RequestParam String country) {

        return productRepo.findByCountry(country).stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getName(),
                        pricingService.finalPrice(p)
                ))
                .toList();
    }
}
