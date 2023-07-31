package com.Estructura.API.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "previousProject")
public class PreviousProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(columnDefinition = "numeric(10,2)")
    private Double cost;

    @Column(nullable = true)
    private String location;

    private boolean projectFromEstructura=false;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id")
    @JsonIgnore
    private Professional professional;

}
