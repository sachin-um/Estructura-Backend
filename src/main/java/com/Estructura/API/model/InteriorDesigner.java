package com.Estructura.API.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class InteriorDesigner extends ServiceProvider{
    private String sLIDRegNumber;

}
