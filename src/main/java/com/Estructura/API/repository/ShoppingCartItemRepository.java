package com.Estructura.API.repository;

import com.Estructura.API.model.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem,Long> {
}
