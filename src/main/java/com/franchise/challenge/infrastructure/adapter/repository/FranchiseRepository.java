package com.franchise.challenge.infrastructure.adapter.repository;

import com.franchise.challenge.infrastructure.adapter.entity.FranchiseEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface FranchiseRepository extends ReactiveMongoRepository<FranchiseEntity, String> {
    Mono<Boolean> existsByNameIgnoreCase(String name);
    Mono<FranchiseEntity> findByNameIgnoreCase(String name);

}
