package com.franchise.challenge.domain.spi;
import com.franchise.challenge.domain.model.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchisePersistencePort {

    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> findById(String id);
    Mono<Boolean> existsByNameIgnoreCase(String name);
    Mono<Franchise> findByNameIgnoreCase(String name);
    Mono<Void> deleteById(String id);
    Flux<Franchise> findAll();

}
