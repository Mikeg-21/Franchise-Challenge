package com.franchise.challenge.domain.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BranchTest {

    @Test
    void builderSetsFieldsAndDefaultsAreMutable() {
        Branch branch = Branch.builder().name("Bogota").build();
        assertEquals("Bogota", branch.getName());
        assertNotNull(branch.getProducts());
        assertTrue(branch.getProducts().isEmpty());

        branch.getProducts().add(Product.builder().name("Camisa").stock(10).build());
        assertEquals(1, branch.getProducts().size());
    }

    @Test
    void allArgsAndSettersWork() {
        Product p = Product.builder().name("Zapato").stock(5).build();
        Branch b = new Branch("Medellin", List.of(p));
        assertEquals("Medellin", b.getName());
        assertEquals(1, b.getProducts().size());

        b.setName("Cali");
        assertEquals("Cali", b.getName());
    }
}