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
@Table(name = "customerPlanRetailItems")
@Entity
public class CustomerPlanRetailItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long retailItems;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private CustomerPlan custonerPlan;
}
