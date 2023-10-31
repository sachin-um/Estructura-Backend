package com.Estructura.API.repository;

import com.Estructura.API.model.CustomerPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerPlanRepository extends JpaRepository<CustomerPlan,Long> {

}
