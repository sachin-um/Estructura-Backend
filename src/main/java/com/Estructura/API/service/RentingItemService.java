package com.Estructura.API.service;

import com.Estructura.API.model.*;
import com.Estructura.API.requests.rentingItems.RentingItemRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;

public interface RentingItemService {
    public GenericAddOrUpdateResponse<RentingItemRequest> saveOrUpdateItem(@ModelAttribute RentingItemRequest rentingItemRequest) throws IOException;
    public ResponseEntity<RentingItem> getItemById(Long id);
    public ResponseEntity<List<RentingItem>> getItemsbyRenter(Renter renter);

    public GenericAddOrUpdateResponse<RentingItemRequest> updateItem(@ModelAttribute RentingItemRequest rentingItemRequest,Long id) throws IOException;

    public ResponseEntity<List<RentingItem>> getItemsbyCategory(RentingCategory rentingCategory);
    public GenericDeleteResponse<Long> deleteItem(RentingItem rentingItem);
}
