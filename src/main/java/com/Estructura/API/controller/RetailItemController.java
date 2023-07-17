package com.Estructura.API.controller;

import com.Estructura.API.model.RetailItem;
import com.Estructura.API.model.RetailItemType;
import com.Estructura.API.service.RetailItemService;
import com.sun.mail.imap.protocol.INTERNALDATE;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/retailitems")
public class RetailItemController {

    private final RetailItemService retailItemService;

    @GetMapping("/all")
    List<RetailItem> fetchAllRetailItems() {
        return retailItemService.fetchAll();
    }

    @GetMapping("/type/{type}")
    List<RetailItem> fetchByType(@PathVariable("type") String type) {
        RetailItemType retailItemType;
        switch (type.toUpperCase()) {
            case "FURNITURE" -> retailItemType = RetailItemType.FURNITURE;
            case "HARDWARE" -> retailItemType = RetailItemType.HARDWARE;
            case "GARDENWARE" -> retailItemType = RetailItemType.GARDENWARE;
            case "BATHWARE" -> retailItemType = RetailItemType.BATHWARE;
            case "LIGHTING" -> retailItemType = RetailItemType.LIGHTING;
            default -> {
                // Handle invalid type
                return Collections.emptyList();
            }
        }
        return retailItemService.fetchByType(retailItemType);
    }

    @GetMapping("/id/{id}")
    List<RetailItem> fetchByID(@PathVariable("id") Long id){
        System.out.println("ID: " + id);
        return retailItemService.fetchByID(id);
    }

    @PostMapping("/additem")
    public void addRetailItem(@RequestBody RetailItem retailItem) {

        RetailItem obj1 = new RetailItem(
                RetailItemType.GARDENWARE,
                "Plant",
                1999.99,
                "plant.jpg",
                "Comfortable plant for your living room",
                10
        );

        System.out.println(obj1.getDescription());
        retailItemService.addRetailItem(retailItem);
        retailItemService.addRetailItem(obj1);
    }

}
