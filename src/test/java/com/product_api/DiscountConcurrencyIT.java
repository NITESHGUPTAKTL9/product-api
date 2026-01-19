package com.product_api;

import com.product_api.repository.ProductDiscountRepository;
import com.product_api.repository.ProductRepository;
import com.product_api.model.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DiscountConcurrencyIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductDiscountRepository pdRepo;

    @Autowired
    private ProductRepository productRepo;

    @BeforeEach
    void setup() {
        productRepo.save(
                new ProductEntity("p1", "Test Product", new BigDecimal("100.00"), "Germany")
        );
    }

    @Test
    void concurrent_discount_requests_are_idempotent() throws Exception {

        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch ready = new CountDownLatch(threads);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    ready.countDown();
                    start.await();

                    mockMvc.perform(
                            put("/products/p1/discount")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                        {
                                          "discountId": "WELCOME10",
                                          "percent": 10
                                        }
                                        """)
                    ).andExpect(status().isOk());

                } catch (Exception ignored) {
                } finally {
                    done.countDown();
                }
            });
        }

        ready.await();
        start.countDown();
        done.await();

        long count = pdRepo.countByIdProductIdAndIdDiscountId("p1", "WELCOME10");
        assertThat(count).isEqualTo(1);

        executor.shutdown();
    }
}
