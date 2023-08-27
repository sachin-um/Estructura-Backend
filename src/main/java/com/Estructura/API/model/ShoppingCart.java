package com.Estructura.API.model;

//import com.Estructura.API.userinfo.UserInfo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class ShoppingCart {
    @Id
    @SequenceGenerator(
        name = "cart_sequence",
        allocationSize = 1
    )

    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "cart_sequence"
    )

    private Long id;

    @OneToMany
    private List<OrderEntity> retailItems = new ArrayList<>();
}
