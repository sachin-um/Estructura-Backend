package com.Estructura.API.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="admin")
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Admin extends User{
    @Column(nullable = false)
    private String assignedArea;
}
