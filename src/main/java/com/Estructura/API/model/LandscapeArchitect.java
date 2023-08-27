package com.Estructura.API.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
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
    private String sliaRegNumber;
    @OneToMany(mappedBy = "landscapeArchitect")
    private List<Specialization> specializations;
    @OneToMany(mappedBy = "landscapeArchitect")
    private List<Qualification> qualifications;
}
