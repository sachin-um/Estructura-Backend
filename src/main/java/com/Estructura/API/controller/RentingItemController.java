package com.Estructura.API.controller;

import com.Estructura.API.model.*;
import com.Estructura.API.requests.rentingItems.RentingItemRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.service.RenterService;
import com.Estructura.API.service.RentingItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/renting-items")
public class RentingItemController {
    private final RentingItemService rentingItemService;
    private final RenterService renterService;
    @PostMapping("/add")
    public GenericAddOrUpdateResponse<RentingItemRequest> addRentingItem(
            @ModelAttribute RentingItemRequest rentingItemRequest) throws IOException {
        return rentingItemService.saveOrUpdateItem(rentingItemRequest);



    }
    @GetMapping("/item/{itemId}") // resp entity <Project>
    public ResponseEntity<RentingItem> RentingItem(@PathVariable("itemId") Long itemId){
        return rentingItemService.getItemById(itemId);


    }
    //
    @GetMapping("/all/{userid}") // resp ent <List<Project
    public ResponseEntity<List<RentingItem>> getRentingItems(@PathVariable("userid") int userid){
        Optional<Renter> renter=renterService.findById(userid);
        if (renter.isPresent()){
            return rentingItemService.getItemsbyRenter(renter.get());
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/all/{category}") // resp ent <List<Project
    public ResponseEntity<List<RentingItem>> getAllRentingItemsByCategory(@PathVariable("category") RentingCategory category){
        return rentingItemService.getItemsbyCategory(category);
    }
    //
    @PostMapping("/update/{itemId}") // equal to add
    public GenericAddOrUpdateResponse<RentingItemRequest> updateItem(
            @PathVariable("itemId") Long itemId,
            @ModelAttribute RentingItemRequest rentingItemRequest) throws IOException {
        GenericAddOrUpdateResponse<RentingItemRequest> response=new GenericAddOrUpdateResponse<>();
        if (rentingItemService.getItemById(itemId).getStatusCode().is2xxSuccessful()){
            return rentingItemService.saveOrUpdateItem(rentingItemRequest);
        }
        else {
            response.addError("fatal","project not found");
            return response;
        }

    }
    //
    @DeleteMapping("/delete/{itemId}") // generic bool
    public GenericDeleteResponse<Long> deleteItem(@PathVariable("itemId") Long itemId) {
        GenericDeleteResponse<Long> response=new GenericDeleteResponse<>();
        ResponseEntity<RentingItem> item=rentingItemService.getItemById(itemId);

        if (item.getStatusCode().is2xxSuccessful()) {
            return rentingItemService.deleteItem(item.getBody());

        } else {
            response.setSuccess(false);
            return response;
        }
    }
}
