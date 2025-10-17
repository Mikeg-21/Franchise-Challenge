package com.franchise.challenge.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void builderAndSettersWork() {
        Product p = Product.builder().name("Camisa").stock(10).build();
        assertEquals("Camisa", p.getName());
        assertEquals(10, p.getStock());

        p.setName("Pantalon");
        p.setStock(7);
        assertEquals("Pantalon", p.getName());
        assertEquals(7, p.getStock());
    }

    @Test
    void noArgsConstructorWorks() {
        Product p = new Product();
        p.setName("Zapato");
        p.setStock(3);
        assertEquals("Zapato", p.getName());
        assertEquals(3, p.getStock());
    }
}