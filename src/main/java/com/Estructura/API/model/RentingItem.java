package com.Estructura.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "rentingItem")
public class RentingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;


    @Column(columnDefinition = "numeric(10,2)", nullable = false)
    private Double price;
    @Column(nullable = false)
    private String scale;

    @Column(nullable = false)
    private RentingCategory category;

    @Column(nullable = true)
    private String MainImage;
    @Column(nullable = true)
    private String MainImageName;

    @Column(nullable = true)
    private String ExtraImage1;
    @Column(nullable = true)
    private String ExtraImage1Name;

    @Column(nullable = true)
    private String ExtraImage2;
    @Column(nullable = true)
    private String ExtraImage2Name;

    @Column(nullable = true)
    private String ExtraImage3;
    @Column(nullable = true)
    private String ExtraImage3Name;
    @Column( nullable = false, updatable = false)
    @CreationTimestamp
    private Date dateAdded;

    private Integer createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id")
    @JsonIgnore
    private Renter renter;
}
