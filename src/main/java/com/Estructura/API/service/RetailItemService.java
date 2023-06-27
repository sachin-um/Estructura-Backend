package com.Estructura.API.service;

import com.Estructura.API.model.RetailItem;

import com.Estructura.API.model.RetailItemType;

import java.util.List;
import java.util.Optional;


public interface RetailItemService {

    public List<RetailItem> fetchAll();
    public List<RetailItem> fetchAllRetailItems();

    public Optional<RetailItem> findRetailItemById(Long id);

    public List<RetailItem> fetchByType(RetailItemType type);

    public void addRetailItem();
}
