package com.Estructura.API.service;

import com.Estructura.API.model.Renter;
import com.Estructura.API.model.RentingCategory;
import com.Estructura.API.model.RentingItem;
import com.Estructura.API.requests.rentingItems.RentingItemRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;

public interface RentingItemService {
    GenericAddOrUpdateResponse<RentingItemRequest> saveOrUpdateItem(
        @ModelAttribute
        RentingItemRequest rentingItemRequest) throws IOException;

    ResponseEntity<List<RentingItem>> getAllItem();

    ResponseEntity<RentingItem> getItemById(Long id);

    ResponseEntity<List<RentingItem>> getItemsbyRenter(Renter renter);

    GenericAddOrUpdateResponse<RentingItemRequest> updateItem(
        @ModelAttribute RentingItemRequest rentingItemRequest,
        Long id) throws IOException;

    ResponseEntity<List<RentingItem>> getItemsbyCategory(
        RentingCategory rentingCategory);

    GenericDeleteResponse<Long> deleteItem(RentingItem rentingItem);
}
