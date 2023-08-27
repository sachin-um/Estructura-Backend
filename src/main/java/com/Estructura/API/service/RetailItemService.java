package com.Estructura.API.service;

import com.Estructura.API.model.RetailItem;
import com.Estructura.API.model.RetailItemType;
import com.Estructura.API.model.RetailStore;
import com.Estructura.API.requests.retailItems.RetailItemRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;


public interface RetailItemService {


    GenericAddOrUpdateResponse<RetailItemRequest> saveItem(
        @ModelAttribute RetailItemRequest retailItemRequest) throws IOException;

    ResponseEntity<List<RetailItem>> getAllItems();

    ResponseEntity<RetailItem> getItemById(Long id);

    ResponseEntity<List<RetailItem>> getItemsByType(RetailItemType type);

    ResponseEntity<List<RetailItem>> getItemsByRetailStore(
        RetailStore retailStore);

    GenericAddOrUpdateResponse<RetailItemRequest> updateItem(
        @ModelAttribute RetailItemRequest retailItemRequest,
        Long id) throws IOException;

    GenericDeleteResponse<Long> deleteItem(RetailItem retailItem);

}
