package com.Estructura.API.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="constructionCompany")
@Entity
@PrimaryKeyJoinColumn(name="id")
public class ConstructionCompany extends ServiceProvider{
    @Column(nullable = false)
    private String businessRegNumber;
    @Column(nullable = false)
    private String businessName;
    @Column(columnDefinition = "numeric(20,2)")
    private Double minRate;
    @Column(columnDefinition = "numeric(20,2)")
    private Double maxRate;

    @OneToMany(mappedBy = "constructionCompany")
    private List<Specialization> specializations;
    @OneToMany(mappedBy = "constructionCompany")
    private List<Qualification> awards;
}
