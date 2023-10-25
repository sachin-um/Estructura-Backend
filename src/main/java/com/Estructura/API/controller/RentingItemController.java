package com.Estructura.API.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Estructura.API.model.Renter;
import com.Estructura.API.model.RentingCategory;
import com.Estructura.API.model.RentingItem;
import com.Estructura.API.requests.rentingItems.RentingItemRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.service.RenterService;
import com.Estructura.API.service.RentingItemService;

import lombok.AllArgsConstructor;

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
    @GetMapping("/all")
    public ResponseEntity<List<RentingItem>> getAllRentingItem(){
        return rentingItemService.getAllItem();
    }
    //
    @GetMapping("/allByUser/{userid}") // resp ent <List<Project
    public ResponseEntity<List<RentingItem>> getRentingItems(@PathVariable("userid") int userid){
        Optional<Renter> renter=renterService.findById(userid);
        if (renter.isPresent()){
            return rentingItemService.getItemsbyRenter(renter.get());
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/allByCategory/{category}") // resp ent <List<Project
    public ResponseEntity<List<RentingItem>> getAllRentingItemsByCategory(@PathVariable("category") RentingCategory category){
        return rentingItemService.getItemsbyCategory(category);
    }
    //
    @PostMapping("/update/{itemId}") // equal to add
    public GenericAddOrUpdateResponse<RentingItemRequest> updateItem(
            @PathVariable("itemId") Long itemId,
            @ModelAttribute RentingItemRequest rentingItemRequest) throws IOException {
       return rentingItemService.updateItem(rentingItemRequest,itemId);

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
