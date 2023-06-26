package com.Estructura.API.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ConstructionCompany extends ServiceProvider{
    private String businessRegNumber;
    private Integer teamSize;
}
