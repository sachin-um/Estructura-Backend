package com.Estructura.API.repository;

import com.Estructura.API.model.CustomerPlanRetailItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerPlanRetailIItemRepository extends JpaRepository<CustomerPlanRetailItems,Long> {
}
