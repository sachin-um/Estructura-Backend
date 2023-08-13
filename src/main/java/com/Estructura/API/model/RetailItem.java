package com.Estructura.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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

    @Column(name = "item_Name",length = 255,nullable = false)
    private String name;

    @Column(name="price", columnDefinition = "numeric(20,2)")
    private Double price;

    @Column(nullable = false)
    private String MainImage;
    @Column(nullable = false)
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
    @Column( nullable = false)
    @CreationTimestamp
    private Date dateAdded;

    @Column(nullable = false,length =1500)
    private String description;

    @Column(nullable = false)
    private Integer quantity;// different quantity types

    private Integer createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "retailStore_id")
    @JsonIgnore
    private RetailStore retailStore;
//    public RetailItem(RetailItemType retailItemType, String name, Double price, String image, String description, Integer quantity){
//        this.retailItemType = retailItemType;
//        this.name = name;
//        this.price = price;
//        this.image = image;
//        this.description = description;
//        this.quantity = quantity;
//    }


}
