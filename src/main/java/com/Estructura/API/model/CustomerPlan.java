package com.Estructura.API.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "customer_plan")
public class CustomerPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String planName;
    private Integer coverImageId;
    private Integer createdBy;

    @Column(nullable = false)
    @CreationTimestamp
    private Date dateCreated;

    @OneToMany(mappedBy = "customerPlan")
    private List<CustomerPlanProfessionals> customerPlanProfessionals;

    @OneToMany(mappedBy = "customerPlan")
    private List<CustomerPlanRetailItems> customerPlanRetailItems;

    @OneToMany(mappedBy = "customerPlan")
    private List<CustomerPlanRentingItems> customerPlanRentingItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

}
