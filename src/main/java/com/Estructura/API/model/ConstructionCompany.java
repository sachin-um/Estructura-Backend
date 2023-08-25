package com.Estructura.API.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "constructionCompany")
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ConstructionCompany extends Professional {
    @Column(nullable = false)
    private String businessRegNumber;
    @OneToMany(mappedBy = "constructionCompany")
    private List<Specialization> specializations;
    @OneToMany(mappedBy = "constructionCompany")
    private List<Qualification> awards;
}
