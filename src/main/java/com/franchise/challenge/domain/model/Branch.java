package com.franchise.challenge.domain.model;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    private String name;
    @Builder.Default private List<Product> products = List.of();

}