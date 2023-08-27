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
@Table(name = "architect")
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Architect extends Professional {
    @Column(nullable = false)
    private String sliaRegNumber;

    @OneToMany(mappedBy = "architect")
    private List<Specialization> specializations;

    @OneToMany(mappedBy = "architect")
    private List<Qualification> qualifications;
}
