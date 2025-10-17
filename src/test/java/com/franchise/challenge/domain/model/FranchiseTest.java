package com.franchise.challenge.domain.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FranchiseTest {

    @Test
    void builderDefaultsMutable() {
        Franchise f = Franchise.builder().id("f1").name("Demo").build();
        assertEquals("f1", f.getId());
        assertEquals("Demo", f.getName());
        assertNotNull(f.getBranches());
        assertTrue(f.getBranches().isEmpty());

        f.getBranches().add(Branch.builder().name("Bogota").build());
        assertEquals(1, f.getBranches().size());
    }

    @Test
    void allArgsAndSettersWork() {
        Franchise f = new Franchise("id","name", List.of());
        assertEquals("id", f.getId());
        assertEquals("name", f.getName());

        f.setId("id2");
        f.setName("name2");
        assertEquals("id2", f.getId());
        assertEquals("name2", f.getName());
    }
}