package com.Estructura.API.repository;

import com.Estructura.API.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEntityRepository extends
    JpaRepository<OrderEntity, Long> {
}
