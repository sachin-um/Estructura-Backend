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
    @Column(nullable = false, length = 1500)
    private String description;
    @Column(columnDefinition = "numeric(20,2)", nullable = false)
    private Double price;
    @Column(nullable = false)
    private String scale;
    @Column(nullable = false)
    private RentingCategory category;
    @Column()
    private String mainImage;
    @Column()
    private String mainImageName;
    @Column()
    private String extraImage1;
    @Column()
    private String extraImage1Name;
    @Column()
    private String extraImage2;
    @Column()
    private String extraImage2Name;
    @Column()
    private String extraImage3;
    @Column()
    private String extraImage3Name;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date dateAdded;
    private Integer createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id")
    @JsonIgnore
    private Renter renter;
}
