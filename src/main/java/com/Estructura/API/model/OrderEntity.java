package com.Estructura.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "order_item")
public class OrderEntity {
    @Id
    @SequenceGenerator(
        name = "order_entity_sequence", allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE, generator = "order_entity_sequence"
    )
    private Long id;
    private Integer quantity;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "retail_item")
    private RetailItem retailItem;
    //check the relationship

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
