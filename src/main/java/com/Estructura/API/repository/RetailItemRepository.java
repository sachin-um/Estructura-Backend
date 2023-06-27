package com.Estructura.API.repository;

import com.Estructura.API.model.RetailItem;
import com.Estructura.API.model.RetailItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetailItemRepository extends JpaRepository<RetailItem, Long> {

    List<RetailItem> findAllByRetailItemType(RetailItemType retailItemType);
}
