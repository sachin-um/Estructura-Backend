package com.Estructura.API.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="architect")
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Architect extends Professional{
    @Column(nullable = false)
    private String sLIARegNumber;
}
