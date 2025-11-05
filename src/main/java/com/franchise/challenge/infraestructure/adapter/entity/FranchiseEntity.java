package com.franchise.challenge.infraestructure.adapter.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "franchises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranchiseEntity {
    @Id
    private String id;
    private String name;
    private List<BranchEntity> branches;
}
