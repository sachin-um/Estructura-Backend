package com.Estructura.API.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="electrician")
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Electrician extends ServiceProvider{
    @OneToMany(mappedBy = "electrician")
    private List<Specialization> specializations;

    @OneToMany(mappedBy = "electrician")
    private List<Qualification> qualifications;
}
