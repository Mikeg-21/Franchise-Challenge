package com.franchise.challenge.infraestructure.adapter.entity;

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