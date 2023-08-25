package com.Estructura.API.model;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
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
@Table(name = "masonWorker")
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class MasonWorker extends Professional {
    @OneToMany(mappedBy = "masonWorker")
    private List<Specialization> specializations;
    @OneToMany(mappedBy = "masonWorker")
    private List<Qualification> qualifications;
}
