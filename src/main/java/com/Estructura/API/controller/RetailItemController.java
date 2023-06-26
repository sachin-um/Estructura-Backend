package com.Estructura.API.controller;

import com.Estructura.API.model.RetailItem;
import com.Estructura.API.service.RetailItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/retailitem")
public class RetailItemController {

    @Autowired
    private final RetailItemService retailItemService;

    @PostMapping("/additem")
    public void addRetailItem(){

    }

    public List<RetailItem> retailItems(){
        return retailItemService.findAll();
    }
}
