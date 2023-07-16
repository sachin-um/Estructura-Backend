package com.Estructura.API.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="interiorDesigner")
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class InteriorDesigner extends Professional{
    @Column(nullable = false)
    private String sLIDRegNumber;

    @OneToMany(mappedBy = "interiorDesigner")
    private List<Specialization> specializations;
    @OneToMany(mappedBy = "interiorDesigner")
    private List<Qualification> qualifications;

}
