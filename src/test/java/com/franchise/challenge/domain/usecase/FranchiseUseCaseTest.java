package com.franchise.challenge.domain.usecase;

import com.franchise.challenge.domain.api.FranchiseServicePort;
import com.franchise.challenge.domain.model.Branch;
import com.franchise.challenge.domain.model.Franchise;
import com.franchise.challenge.domain.model.Product;
import com.franchise.challenge.domain.spi.FranchisePersistencePort;
import com.franchise.challenge.domain.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FranchiseUseCaseTest {

    private FranchisePersistencePort repo;
    private FranchiseServicePort useCase;

    private Franchise base;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(FranchisePersistencePort.class);
        useCase = new FranchiseUseCase(repo);

        base = Franchise.builder()
                .id("f1")
                .name("Demo")
                .branches(new ArrayList<>())
                .build();
    }

    @Test
    void create_ok_whenNotExists() {
        when(repo.existsByNameIgnoreCase("Demo")).thenReturn(Mono.just(false));
        when(repo.save(any(Franchise.class))).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(useCase.create("Demo"))
                .expectNextMatches(fr -> fr.getName().equals("Demo"))
                .verifyComplete();
    }

    @Test
    void create_error_whenExists() {
        when(repo.existsByNameIgnoreCase("Demo")).thenReturn(Mono.just(true));

        StepVerifier.create(useCase.create("Demo"))
                .expectErrorMatches(e -> e instanceof IllegalArgumentException
                        && e.getMessage().equals(Constants.ERR_FRANCHISE_EXISTS))
                .verify();
    }

    @Test
    void rename_ok() {
        when(repo.findById("f1")).thenReturn(Mono.just(base));
        when(repo.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(useCase.rename("f1", "NewName"))
                .expectNextMatches(fr -> fr.getName().equals("NewName"))
                .verifyComplete();
    }

    @Test
    void rename_error_notFound() {
        when(repo.findById("x")).thenReturn(Mono.empty());

        StepVerifier.create(useCase.rename("x", "NewName"))
                .expectErrorMessage(Constants.ERR_FRANCHISE_NOT_FOUND)
                .verify();
    }

    @Test
    void addBranch_ok() {
        when(repo.findById("f1")).thenReturn(Mono.just(base));
        when(repo.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(useCase.addBranch("f1", "Bogota"))
                .expectNextMatches(fr -> fr.getBranches().size() == 1
                        && fr.getBranches().get(0).getName().equals("Bogota"))
                .verifyComplete();
    }

    @Test
    void addBranch_error_franchiseNotFound() {
        when(repo.findById("x")).thenReturn(Mono.empty());

        StepVerifier.create(useCase.addBranch("x", "Bogota"))
                .expectErrorMessage(Constants.ERR_FRANCHISE_NOT_FOUND)
                .verify();
    }

    @Test
    void addBranch_error_branchExists() {
        base.getBranches().add(Branch.builder().name("Bogota").products(new ArrayList<>()).build());
        when(repo.findById("f1")).thenReturn(Mono.just(base));

        StepVerifier.create(useCase.addBranch("f1", "Bogota"))
                .expectErrorMessage(Constants.ERR_BRANCH_EXISTS)
                .verify();
    }

    @Test
    void renameBranch_ok() {
        base.getBranches().add(Branch.builder().name("Old").products(new ArrayList<>()).build());
        when(repo.findById("f1")).thenReturn(Mono.just(base));
        when(repo.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(useCase.renameBranch("f1", "Old", "New"))
                .expectNextMatches(fr -> fr.getBranches().get(0).getName().equals("New"))
                .verifyComplete();
    }

    @Test
    void renameBranch_error_franchiseNotFound() {
        when(repo.findById("no")).thenReturn(Mono.empty());

        StepVerifier.create(useCase.renameBranch("no", "Old", "New"))
                .expectErrorMessage(Constants.ERR_FRANCHISE_NOT_FOUND)
                .verify();
    }

    @Test
    void renameBranch_error_branchNotFound() {
        when(repo.findById("f1")).thenReturn(Mono.just(base));

        StepVerifier.create(useCase.renameBranch("f1", "Nope", "New"))
                .expectErrorMessage(Constants.ERR_BRANCH_NOT_FOUND)
                .verify();
    }

    @Test
    void addProduct_ok() {
        Branch b = Branch.builder().name("Bogota").products(new ArrayList<>()).build();
        base.getBranches().add(b);
        when(repo.findById("f1")).thenReturn(Mono.just(base));
        when(repo.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(useCase.addProduct("f1", "Bogota", "Camisa", 10))
                .expectNextMatches(fr ->
                        fr.getBranches().get(0).getProducts().size() == 1
                                && fr.getBranches().get(0).getProducts().get(0).getName().equals("Camisa"))
                .verifyComplete();
    }

    @Test
    void addProduct_error_franchiseNotFound() {
        when(repo.findById("no")).thenReturn(Mono.empty());

        StepVerifier.create(useCase.addProduct("no", "Bogota", "Camisa", 1))
                .expectErrorMessage(Constants.ERR_FRANCHISE_NOT_FOUND)
                .verify();
    }

    @Test
    void addProduct_error_branchNotFound() {
        when(repo.findById("f1")).thenReturn(Mono.just(base));

        StepVerifier.create(useCase.addProduct("f1", "Nope", "Camisa", 1))
                .expectErrorMessage(Constants.ERR_BRANCH_NOT_FOUND)
                .verify();
    }

    @Test
    void addProduct_error_productExists() {
        Branch b = Branch.builder().name("Bogota").products(new ArrayList<>()).build();
        b.getProducts().add(Product.builder().name("Camisa").stock(1).build());
        base.getBranches().add(b);
        when(repo.findById("f1")).thenReturn(Mono.just(base));

        StepVerifier.create(useCase.addProduct("f1", "Bogota", "Camisa", 1))
                .expectErrorMessage(Constants.ERR_PRODUCT_EXISTS)
                .verify();
    }

    @Test
    void renameProduct_ok() {
        Branch b = Branch.builder().name("Bogota").products(new ArrayList<>()).build();
        b.getProducts().add(Product.builder().name("Camisa").stock(1).build());
        base.getBranches().add(b);
        when(repo.findById("f1")).thenReturn(Mono.just(base));
        when(repo.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(useCase.renameProduct("f1", "Bogota", "Camisa", "Pantalon"))
                .expectNextMatches(fr ->
                        fr.getBranches().get(0).getProducts().get(0).getName().equals("Pantalon"))
                .verifyComplete();
    }

    @Test
    void renameProduct_error_franchiseNotFound() {
        when(repo.findById("x")).thenReturn(Mono.empty());

        StepVerifier.create(useCase.renameProduct("x", "Bogota", "Camisa", "Pantalon"))
                .expectErrorMessage(Constants.ERR_FRANCHISE_NOT_FOUND)
                .verify();
    }

    @Test
    void renameProduct_error_branchNotFound() {
        when(repo.findById("f1")).thenReturn(Mono.just(base));

        StepVerifier.create(useCase.renameProduct("f1", "Nope", "Camisa", "Pantalon"))
                .expectErrorMessage(Constants.ERR_BRANCH_NOT_FOUND)
                .verify();
    }

    @Test
    void renameProduct_error_productNotFound() {
        Branch b = Branch.builder().name("Bogota").products(new ArrayList<>()).build();
        base.getBranches().add(b);
        when(repo.findById("f1")).thenReturn(Mono.just(base));

        StepVerifier.create(useCase.renameProduct("f1", "Bogota", "Nope", "Pantalon"))
                .expectErrorMessage(Constants.ERR_PRODUCT_NOT_FOUND)
                .verify();
    }

    @Test
    void updateStock_ok() {
        Branch b = Branch.builder().name("Bogota").products(new ArrayList<>()).build();
        b.getProducts().add(Product.builder().name("Camisa").stock(1).build());
        base.getBranches().add(b);
        when(repo.findById("f1")).thenReturn(Mono.just(base));
        when(repo.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(useCase.updateStock("f1", "Bogota", "Camisa", 7))
                .expectNextMatches(fr ->
                        fr.getBranches().get(0).getProducts().get(0).getStock() == 7)
                .verifyComplete();
    }

    @Test
    void updateStock_error_franchiseNotFound() {
        when(repo.findById("x")).thenReturn(Mono.empty());

        StepVerifier.create(useCase.updateStock("x", "Bogota", "Camisa", 1))
                .expectErrorMessage(Constants.ERR_FRANCHISE_NOT_FOUND)
                .verify();
    }

    @Test
    void updateStock_error_branchNotFound() {
        when(repo.findById("f1")).thenReturn(Mono.just(base));

        StepVerifier.create(useCase.updateStock("f1", "Nope", "Camisa", 1))
                .expectErrorMessage(Constants.ERR_BRANCH_NOT_FOUND)
                .verify();
    }

    @Test
    void updateStock_error_productNotFound() {
        Branch b = Branch.builder().name("Bogota").products(new ArrayList<>()).build();
        base.getBranches().add(b);
        when(repo.findById("f1")).thenReturn(Mono.just(base));

        StepVerifier.create(useCase.updateStock("f1", "Bogota", "Nope", 1))
                .expectErrorMessage(Constants.ERR_PRODUCT_NOT_FOUND)
                .verify();
    }


    @Test
    void deleteProduct_ok() {
        Branch b = Branch.builder().name("Bogota").products(new ArrayList<>()).build();
        b.getProducts().add(Product.builder().name("Camisa").stock(1).build());
        base.getBranches().add(b);
        when(repo.findById("f1")).thenReturn(Mono.just(base));
        when(repo.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(useCase.deleteProduct("f1", "Bogota", "Camisa"))
                .expectNextMatches(fr -> fr.getBranches().get(0).getProducts().isEmpty())
                .verifyComplete();
    }

    @Test
    void deleteProduct_error_franchiseNotFound() {
        when(repo.findById("no")).thenReturn(Mono.empty());

        StepVerifier.create(useCase.deleteProduct("no", "Bogota", "Camisa"))
                .expectErrorMessage(Constants.ERR_FRANCHISE_NOT_FOUND)
                .verify();
    }

    @Test
    void deleteProduct_error_branchNotFound() {
        when(repo.findById("f1")).thenReturn(Mono.just(base));

        StepVerifier.create(useCase.deleteProduct("f1", "Nope", "Camisa"))
                .expectErrorMessage(Constants.ERR_BRANCH_NOT_FOUND)
                .verify();
    }

    @Test
    void deleteProduct_error_productNotFound() {
        Branch b = Branch.builder().name("Bogota").products(new ArrayList<>()).build();
        base.getBranches().add(b);
        when(repo.findById("f1")).thenReturn(Mono.just(base));

        StepVerifier.create(useCase.deleteProduct("f1", "Bogota", "Nope"))
                .expectErrorMessage(Constants.ERR_PRODUCT_NOT_FOUND)
                .verify();
    }


    @Test
    void getMaxStockPerBranch_ok_mixed() {
        Branch b1 = Branch.builder().name("B1").products(new ArrayList<>()).build();
        b1.getProducts().add(Product.builder().name("A").stock(1).build());
        b1.getProducts().add(Product.builder().name("B").stock(5).build());

        Branch b2 = Branch.builder().name("B2").products(new ArrayList<>()).build(); // sin productos

        base.getBranches().addAll(List.of(b1, b2));
        when(repo.findById("f1")).thenReturn(Mono.just(base));

        StepVerifier.create(useCase.getMaxStockPerBranch("f1"))
                .expectNextMatches(s -> s.contains("B1"))
                .expectNextMatches(s -> s.contains("B2"))
                .verifyComplete();
    }

    @Test
    void getMaxStockPerBranch_error_notFound() {
        when(repo.findById("x")).thenReturn(Mono.empty());

        StepVerifier.create(useCase.getMaxStockPerBranch("x"))
                .expectErrorMessage(Constants.ERR_FRANCHISE_NOT_FOUND)
                .verify();
    }
}