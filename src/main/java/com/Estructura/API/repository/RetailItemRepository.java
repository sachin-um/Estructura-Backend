package com.Estructura.API.repository;

import com.Estructura.API.model.RetailItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetailItemRepository extends JpaRepository<RetailItem,Integer> {

}
