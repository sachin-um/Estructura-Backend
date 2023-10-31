package com.Estructura.API.repository;

import com.Estructura.API.model.CustomerPlanRentingItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerPlanRentingItemRepository extends JpaRepository<CustomerPlanRentingItems,Long> {
}
