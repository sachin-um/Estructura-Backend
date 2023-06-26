package com.Estructura.API.service;

import com.Estructura.API.model.RetailItem;
import com.Estructura.API.repository.RetailItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetailItemServiceImpl implements RetailItemService {

    @Autowired
    private RetailItemRepository retailItemRepository;

    @Override
    public List<RetailItem> findAll() {
        return null;
    }
}
