package com.Estructura.API.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.Estructura.API.model.RetailItem;
import com.Estructura.API.model.RetailItemType;
import com.Estructura.API.service.RetailItemService;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/retailitems")
public class RetailItemController {

    private final RetailItemService retailItemService;

    @GetMapping("/all")
    List<RetailItem> fetchAllRetailItems() {
        System.out.println("Hi");
        return retailItemService.fetchAll();
    }

    @GetMapping()
    List<RetailItem> fetchByType(@RequestParam("type") String type) {
        RetailItemType retailItemType;
        switch (type.toUpperCase()) {
            case "FURNITURE":
                retailItemType = RetailItemType.FURNITURE;
                break;
            case "HARDWARE":
                retailItemType = RetailItemType.HARDWARE;
                break;
            case "GARDENWARE":
                retailItemType = RetailItemType.GARDENWARE;
                break;
            case "BATHWARE":
                retailItemType = RetailItemType.BATHWARE;
                break;
            default:
                // Handle invalid type
                return Collections.emptyList();
        }
        return retailItemService.fetchByType(retailItemType);
    }

    @PostMapping("/additem")
    public void addRetailItem() {
        System.out.println(1);
    }

}
