package com.Estructura.API.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="admin")
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Admin extends User{
    @Column(nullable = false)
    private String assignedArea;
}
