package com.Estructura.API.repository;

import com.Estructura.API.model.RetailItem;
import com.Estructura.API.model.RetailItemType;
import com.Estructura.API.model.RetailStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RetailItemRepository extends JpaRepository<RetailItem, Long> {

    List<RetailItem> findAllByRetailItemType(RetailItemType retailItemType);

    List<RetailItem> findAllById(Long id);

    List<RetailItem> findAllByRetailStore(RetailStore retailStore);
}
