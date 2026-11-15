
package com.devops;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateProductWithValidPrices() {

        Product product = new Product(
                "Laptop",
                "Dell laptop",
                new BigDecimal("100.00"),
                new BigDecimal("500.00")
        );

        assertNotNull(product);
        assertTrue(product.getId() > 0);
    }

    @Test
    void shouldRejectZeroDailyRentalRate() {

        assertThrows(IllegalArgumentException.class, () ->
                new Product(
                        "Laptop",
                        "Dell laptop",
                        BigDecimal.ZERO,
                        new BigDecimal("100.00")
                )
        );
    }

    @Test
    void shouldRejectNegativeDailyRentalRate() {

        assertThrows(IllegalArgumentException.class, () ->
                new Product(
                        "Laptop",
                        "Dell laptop",
                        new BigDecimal("-10.00"),
                        new BigDecimal("100.00")
                )
        );
    }

    @Test
    void shouldRejectNegativeDeposit() {

        assertThrows(IllegalArgumentException.class, () ->
                new Product(
                        "Laptop",
                        "Dell laptop",
                        new BigDecimal("100.00"),
                        new BigDecimal("-10.00")
                )
        );
    }

    @Test
    void shouldRejectNullDailyRentalRate() {

        assertThrows(IllegalArgumentException.class, () ->
                new Product(
                        "Laptop",
                        "Dell laptop",
                        null,
                        new BigDecimal("100.00")
                )
        );
    }

    @Test
    void shouldRejectNullDeposit() {

        assertThrows(IllegalArgumentException.class, () ->
                new Product(
                        "Laptop",
                        "Dell laptop",
                        new BigDecimal("100.00"),
                        null
                )
        );
    }
}
