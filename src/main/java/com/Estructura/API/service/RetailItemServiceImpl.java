package com.Estructura.API.service;

import com.Estructura.API.model.RetailItem;
import com.Estructura.API.model.RetailItemType;
import com.Estructura.API.model.RetailStore;
import com.Estructura.API.requests.retailItems.RetailItemRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.utils.FileUploadUtil;
import lombok.AllArgsConstructor;
import com.Estructura.API.repository.RetailItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RetailItemServiceImpl implements RetailItemService {

    private RetailItemRepository retailItemRepository;
    private final RetailStoreService retailStoreService;

    @Override
    public GenericAddOrUpdateResponse<RetailItemRequest> saveItem(RetailItemRequest retailItemRequest) throws IOException {
        GenericAddOrUpdateResponse<RetailItemRequest> response=new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(retailItemRequest)){
            Optional<RetailStore> retailStore=retailStoreService.findById(retailItemRequest.getRetailStoreId());
            if (retailStore.isPresent()){
                RetailItem item=RetailItem.builder()
                        .name(retailItemRequest.getName())
                        .description(retailItemRequest.getDescription())
                        .price(retailItemRequest.getPrice())
                        .retailItemType(retailItemRequest.getRetailItemType())
                        .createdBy(retailItemRequest.getRetailStoreId())
                        .retailStore(retailStore.get())
                        .quantity(retailItemRequest.getQuantity())
                        .build();
                saveImages(retailItemRequest,item);
                RetailItem retailItem=retailItemRepository.save(item);
                String uploadDir = "./files/retail-item-files/" + retailItem.getRetailStore().getId() + "/" + retailItem.getId();
                FileUploadUtil.uploadImages(uploadDir, retailItemRequest.getMainImage(), retailItem.getMainImageName(),retailItemRequest.getExtraImages(), retailItem.getExtraImage1Name(), retailItem.getExtraImage2Name(), retailItem.getExtraImage3Name());
                response.setSuccess(true);
                response.setId(retailItem.getId());
            }
            else {
                response.addError("fatal", "Invalid Professional ID");
            }
            return response;
        }
        return response;
    }

    private void saveImages(RetailItemRequest retailItemRequest, RetailItem item) {
        String mainImageName=null;
        if (retailItemRequest.getMainImage()!=null) {
            mainImageName = StringUtils.cleanPath(retailItemRequest.getMainImage().getOriginalFilename());
        }
        if (mainImageName!=null){
            item.setMainImage(mainImageName);
            item.setMainImageName(FileUploadUtil.generateFileName(mainImageName));
        }
        int count=0;
        if (retailItemRequest.getExtraImages()!=null) {
            for (MultipartFile file : retailItemRequest.getExtraImages()) {
                if (!file.isEmpty()) {
                    String extraImageName = StringUtils.cleanPath(file.getOriginalFilename());
                    if (count == 0) {
                        item.setExtraImage1(extraImageName);
                        item.setExtraImage1Name(FileUploadUtil.generateFileName(extraImageName));//check the image count is less than 3
                    }
                    if (count == 1) {
                        item.setExtraImage2(extraImageName);
                        item.setExtraImage2Name(FileUploadUtil.generateFileName(extraImageName));
                    }
                    if (count == 2) {
                        item.setExtraImage3(extraImageName);
                        item.setExtraImage3Name(FileUploadUtil.generateFileName(extraImageName));
                    }

                    count++;
                }
            }
        }
    }

    @Override
    public ResponseEntity<List<RetailItem>> getAllItems() {
        List<RetailItem> retailItems= retailItemRepository.findAll();
        if (!retailItems.isEmpty()){
            return  ResponseEntity.ok(retailItems);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<RetailItem> getItemById(Long id) {
        Optional<RetailItem> item=retailItemRepository.findById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<RetailItem>> getItemsByType(RetailItemType type) {
        List<RetailItem> retailItems= retailItemRepository.findAllByRetailItemType(type);
        if (!retailItems.isEmpty()){
            return  ResponseEntity.ok(retailItems);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<List<RetailItem>> getItemsByRetailStore(RetailStore retailStore) {
        List<RetailItem> retailItems= retailItemRepository.findAllByRetailStore(retailStore);
        if (!retailItems.isEmpty()){
            return  ResponseEntity.ok(retailItems);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public GenericAddOrUpdateResponse<RetailItemRequest> updateItem(RetailItemRequest retailItemRequest,Long id) throws IOException {
        GenericAddOrUpdateResponse<RetailItemRequest> response=new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(retailItemRequest)){
            Optional<RetailItem> existingItem=retailItemRepository.findById(id);
            if (existingItem.isPresent()){
                RetailItem item=existingItem.get();
                item.setName(retailItemRequest.getName());
                item.setDescription(retailItemRequest.getDescription());
                item.setPrice(retailItemRequest.getPrice());
                item.setRetailItemType(retailItemRequest.getRetailItemType());
                item.setQuantity(retailItemRequest.getQuantity());
                saveImages(retailItemRequest,item);
                item=retailItemRepository.save(item);
                String uploadDir = "./files/retail-item-files/" + item.getRetailStore().getId() + "/" + item.getId();
                FileUploadUtil.uploadImages(uploadDir, retailItemRequest.getMainImage(), item.getMainImageName(),retailItemRequest.getExtraImages(), item.getExtraImage1Name(), item.getExtraImage2Name(), item.getExtraImage3Name());
                response.setSuccess(true);
                response.setId(item.getId());

            }
            else {
                response.addError("fatal","Item doesn't exist");
            }
        }
        return response;
    }

    @Override
    public GenericDeleteResponse<Long> deleteItem(RetailItem retailItem) {
        GenericDeleteResponse<Long> response=new GenericDeleteResponse<>();
        retailItemRepository.delete(retailItem);
        Optional<RetailItem> item=retailItemRepository.findById(retailItem.getId());
        if (item.isPresent()){
            response.setSuccess(false);
            response.setMessage("Somthing went wrong please try again");

        }
        else {
            response.setSuccess(true);
        }
        return response;
    }
//
//    @Override
//    public List<RetailItem> fetchAll() {
//        return retailItemRepository.findAll();
//    }
//    @Override
//    public Optional<RetailItem> findRetailItemById(Long id){
//        return retailItemRepository.findById(id);
//    }
//
//    @Override
//    public List<RetailItem> fetchByType(RetailItemType retailItemType){
//        return retailItemRepository.findAllByRetailItemType(retailItemType);
//    }
//
//    @Override
//    public List<RetailItem> fetchByID(Long id){
//        return retailItemRepository.findAllById(id);
//    }
//
//    @Override
//    public void addRetailItem(RetailItem retailItem){
//        retailItemRepository.save(retailItem);
//    }

}
