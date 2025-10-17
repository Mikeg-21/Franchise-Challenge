package com.franchise.challenge.infrastructure.entrypoint.mapper;

import com.franchise.challenge.domain.model.Franchise;
import com.franchise.challenge.infrastructure.entrypoint.dto.FranchiseRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FranchiseDtoMapper {
    Franchise toDomain(FranchiseRequest req);
}