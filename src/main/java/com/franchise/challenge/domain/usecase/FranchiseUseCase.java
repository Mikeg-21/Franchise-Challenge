package com.franchise.challenge.domain.usecase;

import com.franchise.challenge.domain.api.FranchiseServicePort;
import com.franchise.challenge.domain.model.Branch;
import com.franchise.challenge.domain.model.Franchise;
import com.franchise.challenge.domain.model.Product;
import com.franchise.challenge.domain.spi.FranchisePersistencePort;
import com.franchise.challenge.domain.util.Constants;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FranchiseUseCase implements FranchiseServicePort {

    private final FranchisePersistencePort franchisePersistencePort;

    @Override
    public Mono<Franchise> create(String name) {
        return franchisePersistencePort.existsByNameIgnoreCase(name)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_FRANCHISE_EXISTS)))
                .flatMap(ignore -> franchisePersistencePort.save(Franchise.builder().name(name).build()));
    }

    @Override
    public Mono<Franchise> rename(String id, String newName) {
        return franchisePersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_FRANCHISE_NOT_FOUND)))
                .map(franchise -> {
                    franchise.setName(newName);
                    return franchise;
                })
                .flatMap(franchisePersistencePort::save);
    }

    @Override
    public Mono<Franchise> addBranch(String id, String branchName) {
        return franchisePersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_FRANCHISE_NOT_FOUND)))
                .filter(fr -> fr.getBranches().stream()
                        .noneMatch(b -> b.getName().equalsIgnoreCase(branchName)))
                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_BRANCH_EXISTS)))
                .map(fr -> {
                    fr.getBranches().add(Branch.builder().name(branchName).build());
                    return fr;
                })
                .flatMap(franchisePersistencePort::save);
    }

    @Override
    public Mono<Franchise> renameBranch(String id, String oldName, String newName) {
        return franchisePersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_FRANCHISE_NOT_FOUND)))
                .flatMap(fr -> Flux.fromIterable(fr.getBranches())
                        .filter(b -> b.getName().equalsIgnoreCase(oldName))
                        .singleOrEmpty()
                        .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_BRANCH_NOT_FOUND)))
                        .map(b -> { b.setName(newName); return fr; }))
                .flatMap(franchisePersistencePort::save);
    }

    @Override
    public Mono<Franchise> addProduct(String id, String branchName, String productName, int stock) {
        return Mono.just(stock)
                .filter(s -> s >= 0)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_STOCK_INVALID)))
                .then(franchisePersistencePort.findById(id))
                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_FRANCHISE_NOT_FOUND)))
                .flatMap(fr -> Flux.fromIterable(fr.getBranches())
                        .filter(b -> b.getName().equalsIgnoreCase(branchName))
                        .singleOrEmpty()
                        .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_BRANCH_NOT_FOUND)))
                        .filter(b -> b.getProducts().stream()
                                .noneMatch(p -> p.getName().equalsIgnoreCase(productName)))
                        .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_PRODUCT_EXISTS)))
                        .map(branch -> {
                            branch.getProducts().add(Product.builder().name(productName).stock(stock).build());
                            return fr;
                        }))
                .flatMap(franchisePersistencePort::save);
    }

    @Override
    public Mono<Franchise> renameProduct(String id, String branchName, String oldName, String newName) {
        return franchisePersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_FRANCHISE_NOT_FOUND)))
                .flatMap(fr -> Flux.fromIterable(fr.getBranches())
                        .filter(b -> b.getName().equalsIgnoreCase(branchName))
                        .singleOrEmpty()
                        .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_BRANCH_NOT_FOUND)))
                        .flatMap(b -> Flux.fromIterable(b.getProducts())
                                .filter(p -> p.getName().equalsIgnoreCase(oldName))
                                .singleOrEmpty()
                                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_PRODUCT_NOT_FOUND)))
                                .map(p -> { p.setName(newName); return fr; })
                        )
                )
                .flatMap(franchisePersistencePort::save);
    }

    @Override
    public Mono<Franchise> updateStock(String id, String branchName, String productName, int stock) {
        return Mono.just(stock)
                .filter(s -> s >= 0)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_STOCK_INVALID)))
                .then(franchisePersistencePort.findById(id))
                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_FRANCHISE_NOT_FOUND)))
                .flatMap(fr -> Flux.fromIterable(fr.getBranches())
                        .filter(b -> b.getName().equalsIgnoreCase(branchName))
                        .singleOrEmpty()
                        .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_BRANCH_NOT_FOUND)))
                        .flatMap(b -> Flux.fromIterable(b.getProducts())
                                .filter(p -> p.getName().equalsIgnoreCase(productName))
                                .singleOrEmpty()
                                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_PRODUCT_NOT_FOUND)))
                                .map(p -> { p.setStock(stock); return fr; })
                        )
                )
                .flatMap(franchisePersistencePort::save);
    }

    @Override
    public Mono<Franchise> deleteProduct(String id, String branchName, String productName) {
        return franchisePersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_FRANCHISE_NOT_FOUND)))
                .flatMap(fr -> Flux.fromIterable(fr.getBranches())
                        .filter(b -> b.getName().equalsIgnoreCase(branchName))
                        .singleOrEmpty()
                        .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_BRANCH_NOT_FOUND)))
                        .flatMap(b -> Flux.fromIterable(b.getProducts())
                                .filter(p -> p.getName().equalsIgnoreCase(productName))
                                .singleOrEmpty()
                                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_PRODUCT_NOT_FOUND)))
                                .map(p -> { b.getProducts().remove(p); return fr; })
                        )
                )
                .flatMap(franchisePersistencePort::save);
    }

    @Override
    public Flux<String> getMaxStockPerBranch(String franchiseId) {
        return franchisePersistencePort.findById(franchiseId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERR_FRANCHISE_NOT_FOUND)))
                .flatMapMany(fr -> Flux.fromIterable(
                        Optional.ofNullable(fr.getBranches()).orElse(List.of())
                ))
                .map(branch -> Flux.fromIterable(
                                        Optional.ofNullable(branch.getProducts()).orElse(List.of())
                                )
                                .reduce((p1, p2) -> p1.getStock() >= p2.getStock() ? p1 : p2)
                                .defaultIfEmpty(Product.builder().name(Constants.NO_PRODUCTS).stock(0).build())
                                .map(p -> String.join(Constants.EMPTY,
                                        branch.getName(),
                                        Constants.SEP_ARROW,
                                        p.getName(),
                                        p.getStock() > 0
                                                ? String.format(Constants.FORMAT_STOCK, p.getStock())
                                                : Constants.EMPTY))
                )
                .flatMap(mono -> mono);
    }

}
