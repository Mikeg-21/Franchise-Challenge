package com.franchise.challenge.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Franchise {

    private String id;
    private String name;
    @Builder.Default private List<Branch> branches = new ArrayList<>();

}
