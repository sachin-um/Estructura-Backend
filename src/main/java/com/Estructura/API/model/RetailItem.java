package com.Estructura.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "retail_Item")
public class RetailItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private RetailItemType retailItemType;
    @Column(name = "item_Name", nullable = false)
    private String name;
    @Column(name = "price", columnDefinition = "numeric(20,2)")
    private Double price;
    @Column(nullable = false)
    private String mainImage;
    @Column(nullable = false)
    private String mainImageName;
    @Column()
    private String extraImage1;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FurnitureType furnitureType;
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
    @Column(nullable = false)
    @CreationTimestamp
    private Date dateAdded;
    @Column(nullable = false, length = 1500)
    private String description;
    @Column(nullable = false)
    private Integer quantity;// different quantity types
    private Integer createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "retailStore_id")
    @JsonIgnore
    private RetailStore retailStore;
}
