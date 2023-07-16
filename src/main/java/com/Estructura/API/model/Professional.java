package com.Estructura.API.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="professional")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="id")
public class Professional extends ServiceProvider{
    private String introduction;
    @Column(columnDefinition = "numeric(10,2)")
    private Double minRate;
    @Column(columnDefinition = "numeric(10,2)")
    private Double maxRate;

    private String businessContactNo;
    @OneToMany(mappedBy = "professional")
    private List<ServiceArea> serviceAreas;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL)
    private List<PreviousProject> previousProjects;
}
