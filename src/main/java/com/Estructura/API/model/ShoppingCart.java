package com.Estructura.API.model;

//import com.Estructura.API.userinfo.UserInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
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
    private List<OrderEntity> retailItems = new ArrayList<OrderEntity>();

//    public ShoppingCart(UserInfo userInfo){
//        this.userInfo = userInfo;
//    }

}
