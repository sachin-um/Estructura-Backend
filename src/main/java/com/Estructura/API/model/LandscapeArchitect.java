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
@Table(name="landscapeArchitect")
@Entity
@PrimaryKeyJoinColumn(name="id")
public class LandscapeArchitect extends Professional{
    @OneToMany(mappedBy = "landscapeArchitect")
    private List<Specialization> specializations;

    @OneToMany(mappedBy = "landscapeArchitect")
    private List<Qualification> qualifications;
}
