package com.Estructura.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

import static com.Estructura.API.model.ResponseStatus.PENDING;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "response")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String shortDesc;
    @Column(length = 2000)
    private String response;
    @Column()
    private Double proposedBudget;
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

    private Integer createBy;

    @Enumerated(EnumType.STRING)
    private ResponseStatus status=PENDING;

    @Column(nullable = false)
    @CreationTimestamp
    private Date dateAdded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    @JsonIgnore
    private CustomerRequest customerRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_provider_id")
    @JsonIgnore
    private ServiceProvider serviceProvider;

}
