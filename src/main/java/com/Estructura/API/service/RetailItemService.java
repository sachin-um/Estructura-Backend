package com.Estructura.API.service;

import com.Estructura.API.model.RetailItem;

import com.Estructura.API.model.RetailItemType;

import java.util.List;
import java.util.Optional;


public interface RetailItemService {

    List<RetailItem> fetchAll();

    Optional<RetailItem> findRetailItemById(Long id);

    List<RetailItem> fetchByType(RetailItemType type);

    void addRetailItem(RetailItem retailItem);
}
