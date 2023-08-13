package com.Estructura.API.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name="customer_request")
public class CustomerRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 2000)
    private String description;
    @Column(nullable = false)
    private String shortDesc;
    @Column(nullable = false)
    private Double minPrice;

    @Column(nullable = false)
    private Double maxPrice;

    @Column(nullable = true)
    private String image1;
    @Column(nullable = true)
    private String image1Name;

    @Column(nullable = true)
    private String image2;
    @Column(nullable = true)
    private String image2Name;

    @Column(nullable = true)
    private String image3;
    @Column(nullable = true)
    private String image3Name;

    @Column(nullable = true)
    private String Document1;
    @Column(nullable = true)
    private String Document1Name;

    @Column(nullable = true)
    private String Document2;
    @Column(nullable = true)
    private String Document2Name;

    @Column(nullable = true)
    private String Document3;

    @Column(nullable = true)
    private String Document3Name;

    private Integer createdBy;

    @Column( nullable = false)
    @CreationTimestamp
    private Date dateAdded;

    @OneToMany(mappedBy = "customerRequest")
    private List<RequestTargetItemType> targetRetailCategories;

    @OneToMany(mappedBy = "customerRequest")
    private List<RequestTargetProfessionalCategory> targetCategories;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

}
