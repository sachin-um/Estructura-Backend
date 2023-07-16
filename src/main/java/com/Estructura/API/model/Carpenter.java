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
@Table(name="carpenter")
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Carpenter extends Professional{
    @OneToMany(mappedBy = "carpenter")
    private List<Specialization> specializations;
    @OneToMany(mappedBy = "carpenter")
    private List<Qualification> qualifications;
}
