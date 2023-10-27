package com.Estructura.API.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @NotBlank
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int senderId;
    @Column(nullable = false)
    private int recipientId;

    @ElementCollection
    private List<String> files;

    @ElementCollection
    private List<String> fileOriginalNames;

    @Column(nullable = false)
    private boolean seen = false;
}
