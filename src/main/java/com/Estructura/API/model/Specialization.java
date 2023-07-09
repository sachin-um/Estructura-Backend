package com.Estructura.API.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="specialization")
@Entity
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String specialization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "architect_id")
    private Architect architect;
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "interiorDesigner_id")
    private InteriorDesigner interiorDesigner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constructionCompany_id")
    private ConstructionCompany constructionCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "painter_id")
    private Painter painter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carpenter_id")
    private Carpenter carpenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electrician_id")
    private Electrician electrician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landscapeArchitect_id")
    private LandscapeArchitect landscapeArchitect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masonWorker_id")
    private MasonWorker masonWorker;


}
