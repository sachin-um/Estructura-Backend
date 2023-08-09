package com.Estructura.API.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Estructura.API.model.RetailItem;
import com.Estructura.API.model.RetailItemType;
import com.Estructura.API.model.RetailStore;

public interface RetailItemRepository extends JpaRepository<RetailItem, Long> {

    List<RetailItem> findAllByRetailItemType(RetailItemType retailItemType);

    List<RetailItem> findAllById(Long id);

    List<RetailItem> findAllByRetailStore(RetailStore retailStore);

}
