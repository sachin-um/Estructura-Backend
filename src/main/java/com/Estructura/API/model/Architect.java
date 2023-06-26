package com.Estructura.API.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Architect extends ServiceProvider{
    private String sLIARegNumber;
}
