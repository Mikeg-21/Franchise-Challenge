package com.franchise.challenge.domain.api;

import com.franchise.challenge.domain.model.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseServicePort {

    Mono<Franchise> create(String name);
    Mono<Franchise> rename(String id, String newName);

    Mono<Franchise> addBranch(String id, String branchName);
    Mono<Franchise> renameBranch(String id, String oldName, String newName);

    Mono<Franchise> addProduct(String id, String branchName, String productName, int stock);
    Mono<Franchise> renameProduct(String id, String branchName, String oldName, String newName);
    Mono<Franchise> updateStock(String id, String branchName, String productName, int stock);
    Mono<Franchise> deleteProduct(String id, String branchName, String productName);

    Flux<String> getMaxStockPerBranch(String franchiseId);
}
