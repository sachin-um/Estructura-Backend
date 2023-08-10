package com.Estructura.API.controller;

import com.Estructura.API.model.RetailItem;
import com.Estructura.API.model.RetailItemType;
import com.Estructura.API.model.RetailStore;
import com.Estructura.API.requests.retailItems.RetailItemRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.responses.GenericResponse;
import com.Estructura.API.service.RetailItemService;
import com.Estructura.API.service.RetailStoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/retailitems")
public class RetailItemController {

    private final RetailItemService retailItemService;
    private final RetailStoreService retailStoreService;
    @PostMapping("/add")
    public GenericAddOrUpdateResponse<RetailItemRequest> addRetaiItem(
            @ModelAttribute RetailItemRequest retailItemRequest) throws IOException {
        return retailItemService.saveItem(retailItemRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RetailItem>> getAllItems(){
        return retailItemService.getAllItems();
    }

    @GetMapping("/category/{type}")
    public ResponseEntity<List<RetailItem>> getAllItemsByType(@PathVariable("type") RetailItemType type){
        return retailItemService.getItemsByType(type);
    }

    @GetMapping("/store/{store_id}")
    public ResponseEntity<List<RetailItem>> getAllItemByRetailStore(@PathVariable("store_id") int id){
        Optional<RetailStore> retailStore=retailStoreService.findById(id);
        if (retailStore.isPresent()){
            return retailItemService.getItemsByRetailStore(retailStore.get());
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/item/{item_id}")
    public  ResponseEntity<RetailItem> retailItem(@PathVariable("item_id") Long itemId){
        return retailItemService.getItemById(itemId);
    }

    @PostMapping("/update/{item_id}")
    public GenericAddOrUpdateResponse<RetailItemRequest> updateItem(@PathVariable("item_id") Long itemid,
                                                                    @ModelAttribute RetailItemRequest retailItemRequest) throws IOException {
        return retailItemService.updateItem(retailItemRequest,itemid);
    }

    @DeleteMapping("/delete/{item_id}")
    public GenericDeleteResponse<Long> deleteItem(@PathVariable("item_id") Long itemId){
        GenericDeleteResponse<Long> response=new GenericDeleteResponse<>();
        ResponseEntity<RetailItem> item=retailItemService.getItemById(itemId);
        if (item.getStatusCode().is2xxSuccessful()){
            return retailItemService.deleteItem(item.getBody());
        }
        else {
            response.setSuccess(false);
            return response;
        }
    }

//
//    @GetMapping("/all")
//    List<RetailItem> fetchAllRetailItems() {
//        return retailItemService.fetchAll();
//    }
//
//    @GetMapping("/type/{type}")
//    List<RetailItem> fetchByType(@PathVariable("type") String type) {
//        RetailItemType retailItemType;
//        switch (type.toUpperCase()) {
//            case "FURNITURE" -> retailItemType = RetailItemType.FURNITURE;
//            case "HARDWARE" -> retailItemType = RetailItemType.HARDWARE;
//            case "GARDENWARE" -> retailItemType = RetailItemType.GARDENWARE;
//            case "BATHWARE" -> retailItemType = RetailItemType.BATHWARE;
//            case "LIGHTING" -> retailItemType = RetailItemType.LIGHTING;
//            default -> {
//                // Handle invalid type
//                return Collections.emptyList();
//            }
//        }
//        return retailItemService.fetchByType(retailItemType);
//    }
//
//    @GetMapping("/id/{id}")
//    List<RetailItem> fetchByID(@PathVariable("id") Long id){
//        System.out.println("ID: " + id);
//        return retailItemService.fetchByID(id);
//    }
//
//    @PostMapping("/additem")
//    public void addRetailItem(@RequestBody RetailItem retailItem) {
//
//        RetailItem obj1 = new RetailItem(
//                RetailItemType.GARDENWARE,
//                "Plant",
//                1999.99,
//                "plant.jpg",
//                "Comfortable plant for your living room",
//                10
//        );
//
//        System.out.println(obj1.getDescription());
//        retailItemService.addRetailItem(retailItem);
//        retailItemService.addRetailItem(obj1);
//    }

}
