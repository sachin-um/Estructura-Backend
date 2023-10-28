package com.Estructura.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "customerPlanProfessionals")
@Entity
public class CustomerPlanProfessionals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer professionalId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private CustomerPlan customerPlan;

}
