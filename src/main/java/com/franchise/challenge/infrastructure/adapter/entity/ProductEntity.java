package com.franchise.challenge.infrastructure.adapter.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {
    private String name;
    private int stock;
}