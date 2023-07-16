package com.Estructura.API.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ConstructionCompany extends ServiceProvider{
    private String businessRegNumber;
    private Integer teamSize;

    @OneToMany(mappedBy = "constructionCompany")
    private List<Specialization> specializations;
    @OneToMany(mappedBy = "constructionCompany")
    private List<Qualification> awards;
}
