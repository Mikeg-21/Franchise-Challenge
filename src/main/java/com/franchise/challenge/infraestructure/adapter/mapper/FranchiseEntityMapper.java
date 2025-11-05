package com.franchise.challenge.infraestructure.adapter.mapper;

import com.franchise.challenge.domain.model.Branch;
import com.franchise.challenge.domain.model.Franchise;
import com.franchise.challenge.domain.model.Product;
import com.franchise.challenge.infraestructure.adapter.entity.BranchEntity;
import com.franchise.challenge.infraestructure.adapter.entity.FranchiseEntity;
import com.franchise.challenge.infraestructure.adapter.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FranchiseEntityMapper {

    FranchiseEntity toEntity(Franchise franchise);
    Franchise toDomain(FranchiseEntity entity);

    BranchEntity toEntity(Branch branch);
    Branch toDomain(BranchEntity branchEntity);

    ProductEntity toEntity(Product product);
    Product toDomain(ProductEntity productEntity);

}
