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
@Table(name="qualification")
@Entity
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String qualification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "architect_id")
    private Architect architect;
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "interiorDesiner_id")
    private InteriorDesigner interiorDesigner;


    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "carpenter_id")
    private Carpenter carpenter;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "constructionCompany_id")
    private ConstructionCompany constructionCompany;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "electrician_id")
    private Electrician electrician;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "landscapeArchitect_id")
    private LandscapeArchitect landscapeArchitect;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "masonWorker_id")
    private MasonWorker masonWorker;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "painter_id")
    private Painter painter;
}
