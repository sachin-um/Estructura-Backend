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

    @Column(nullable = false, length = 3000)
    private String description;

    @Column(columnDefinition = "numeric(20,2)")
    private Double cost;

    @Column()
    private String location;
    private boolean projectFromEstructura = false;
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
    @Column()
    private String Document1;
    @Column()
    private String Document1Name;
    @Column()
    private String Document2;
    @Column()
    private String Document2Name;
    @Column()
    private String Document3;
    @Column()
    private String Document3Name;
    private Integer createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id")
    @JsonIgnore
    private Professional professional;
}
