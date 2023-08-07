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
import java.util.Optional;


public interface RetailItemService {


//    List<RetailItem> fetchAll();
//
//    Optional<RetailItem> findRetailItemById(Long id);
//
//    List<RetailItem> fetchByType(RetailItemType type);
//
//    List<RetailItem> fetchByID(Long id);
//
//    void addRetailItem(RetailItem retailItem);

    public GenericAddOrUpdateResponse<RetailItemRequest> saveItem(@ModelAttribute RetailItemRequest retailItemRequest) throws IOException;

    public ResponseEntity<List<RetailItem>> getAllItems();

    public ResponseEntity<RetailItem> getItemByItem(Long id);

    public ResponseEntity<List<RetailItem>> getItemsByType(RetailItemType type);

    public ResponseEntity<List<RetailItem>> getItemsByRetailStore(RetailStore retailStore);
    public GenericAddOrUpdateResponse<RetailItemRequest> updateItem(@ModelAttribute RetailItemRequest retailItemRequest,Long id) throws IOException;

    public GenericDeleteResponse<Long> deleteItem(RetailItem retailItem);

}
