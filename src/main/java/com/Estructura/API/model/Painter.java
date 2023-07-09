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
@Table(name="painter")
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Painter extends Professional{
    @OneToMany(mappedBy = "painter")
    private List<Specialization> specializations;

    @OneToMany(mappedBy = "painter")
    private List<Qualification> qualifications;
}
