package com.franchise.challenge.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    private String name;
    private List<Product> products = new ArrayList<>();
}