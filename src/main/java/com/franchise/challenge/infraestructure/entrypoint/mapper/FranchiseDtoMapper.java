package com.franchise.challenge.infraestructure.entrypoint.mapper;

import com.franchise.challenge.domain.model.Franchise;
import com.franchise.challenge.infraestructure.entrypoint.dto.FranchiseRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FranchiseDtoMapper {
    Franchise toDomain(FranchiseRequest req);
}