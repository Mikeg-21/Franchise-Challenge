package com.franchise.challenge.infrastructure.adapter.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchEntity {
    private String name;
    private List<ProductEntity> products;
}