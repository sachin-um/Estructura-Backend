package com.Estructura.API.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "landscapeArchitect")
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class LandscapeArchitect extends Professional {
    @Column(nullable = false)
    private String sLIARegNumber;

    @OneToMany(mappedBy = "landscapeArchitect")
    private List<Specialization> specializations;

    @OneToMany(mappedBy = "landscapeArchitect")
    private List<Qualification> qualifications;
}
