package com.franchise.challenge.infraestructure.adapter.persistence;

import com.franchise.challenge.domain.model.Franchise;
import com.franchise.challenge.domain.spi.FranchisePersistencePort;
import com.franchise.challenge.infraestructure.adapter.mapper.FranchiseEntityMapper;
import com.franchise.challenge.infraestructure.adapter.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseAdapter implements FranchisePersistencePort {

    private final FranchiseRepository  franchiseRepository;
    private final FranchiseEntityMapper mapper;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return franchiseRepository.save(mapper.toEntity(franchise))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return franchiseRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Franchise> findAll() {
        return franchiseRepository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByNameIgnoreCase(String name) {
        return franchiseRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public Mono<Franchise> findByNameIgnoreCase(String name) {
        return franchiseRepository.findByNameIgnoreCase(name)
                .map(mapper::toDomain);
    }


    @Override
    public Mono<Void> deleteById(String id) {
        return franchiseRepository.deleteById(id);
    }
}
