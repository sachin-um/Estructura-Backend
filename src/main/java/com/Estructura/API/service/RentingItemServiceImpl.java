package com.Estructura.API.service;

import com.Estructura.API.model.*;
import com.Estructura.API.repository.RentingItemRepository;
import com.Estructura.API.requests.rentingItems.RentingItemRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.utils.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RentingItemServiceImpl implements RentingItemService{
    private final RentingItemRepository rentingItemRepository;
    private final RenterService renterService;
    @Override
    public GenericAddOrUpdateResponse<RentingItemRequest> saveOrUpdateItem(RentingItemRequest rentingItemRequest) throws IOException {
        GenericAddOrUpdateResponse<RentingItemRequest> response=new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(rentingItemRequest)){
            Optional<Renter> renter=renterService.findById(rentingItemRequest.getRenterId());
            if (renter.isPresent()){

                RentingItem item=RentingItem.builder()
                        .name(rentingItemRequest.getName())
                        .description(rentingItemRequest.getDescription())
                        .price(rentingItemRequest.getPrice())
                        .category(rentingItemRequest.getCategory())
                        .scale(rentingItemRequest.getScale())
                        .renter(renter.get())
                        .build();
                saveImages(rentingItemRequest, item);

                RentingItem rentingItem=rentingItemRepository.save(item);
                String uploadDir = "./files/renting-item-files/" + rentingItem.getRenter().getId() + "/" + rentingItem.getId();
                FileUploadUtil.saveImages(uploadDir, rentingItemRequest.getMainImage(), rentingItem.getMainImageName(), rentingItemRequest.getExtraImages(), rentingItem.getExtraImage1Name(), rentingItem.getExtraImage2Name(), rentingItem.getExtraImage3Name());
                response.setSuccess(true);
                response.setId(rentingItem.getId());
                return response;
            }
            else {
                response.addError("fatal","Invalid professional ID");
                return response;
            }

        }
        return response;
    }

    @Override
    public ResponseEntity<List<RentingItem>> getAllItem() {
        List<RentingItem> rentingItems=rentingItemRepository.findAll();
        if (!rentingItems.isEmpty()){
            return ResponseEntity.ok(rentingItems);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }


    @Override
    public ResponseEntity<RentingItem> getItemById(Long id) {
        Optional<RentingItem> item= rentingItemRepository.findById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<RentingItem>> getItemsbyRenter(Renter renter) {
        List<RentingItem> items= rentingItemRepository.findAllByRenter(renter);
        if (!items.isEmpty()){
            return  ResponseEntity.ok(items);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public GenericAddOrUpdateResponse<RentingItemRequest> updateItem(RentingItemRequest rentingItemRequest, Long id) throws IOException {
        GenericAddOrUpdateResponse<RentingItemRequest> response=new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(rentingItemRequest)){
            Optional<RentingItem> existingItem=rentingItemRepository.findById(id);
            if (existingItem.isPresent()){
                RentingItem item=existingItem.get();
                item.setName(rentingItemRequest.getName());
                item.setDescription(rentingItemRequest.getDescription());
                item.setScale(rentingItemRequest.getScale());
                item.setPrice(rentingItemRequest.getPrice());
                item.setCategory(rentingItemRequest.getCategory());
                saveImages(rentingItemRequest, item);
                item=rentingItemRepository.save(item);
                String uploadDir = "./files/renting-item-files/" + item.getRenter().getId() + "/" + item.getId();
                FileUploadUtil.saveImages(uploadDir, rentingItemRequest.getMainImage(), item.getMainImageName(), rentingItemRequest.getExtraImages(), item.getExtraImage1Name(), item.getExtraImage2Name(), item.getExtraImage3Name());
                response.setSuccess(true);
                response.setId(item.getId());
            }
            else {
                response.addError("fatal","Item doesn't exist");
            }
        }
        return response;
    }

    private void saveImages(RentingItemRequest rentingItemRequest, RentingItem item) {
        String mainImageName=null;
        if (rentingItemRequest.getMainImage()!=null) {
            mainImageName = StringUtils.cleanPath(rentingItemRequest.getMainImage().getOriginalFilename());
        }
        if (mainImageName!=null){
            item.setMainImage(mainImageName);
            item.setMainImageName(FileUploadUtil.generateFileName(mainImageName));
        }
        int count=0;
        if (rentingItemRequest.getExtraImages()!=null) {
            for (MultipartFile file : rentingItemRequest.getExtraImages()) {
                if (!file.isEmpty()) {
                    String extraImageName = StringUtils.cleanPath(file.getOriginalFilename());
                    if (count == 0) item.setExtraImage1(extraImageName);
                    item.setExtraImage1Name(FileUploadUtil.generateFileName(extraImageName));//check the image count is less than 3
                    if (count == 1) item.setExtraImage2(extraImageName);
                    item.setExtraImage2Name(FileUploadUtil.generateFileName(extraImageName));
                    if (count == 3) item.setExtraImage3(extraImageName);
                    item.setExtraImage3Name(FileUploadUtil.generateFileName(extraImageName));
                    count++;
                }
            }
        }
    }

    @Override
    public ResponseEntity<List<RentingItem>> getItemsbyCategory(RentingCategory rentingCategory) {
        List<RentingItem> items= rentingItemRepository.findRentingItemsByCategory(rentingCategory);
        if (!items.isEmpty()){
            return  ResponseEntity.ok(items);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public GenericDeleteResponse<Long> deleteItem(RentingItem rentingItem) {
        GenericDeleteResponse<Long> response=new GenericDeleteResponse<>();
        rentingItemRepository.delete(rentingItem);
        Optional<RentingItem> item= rentingItemRepository.findById(rentingItem.getId());
        if (item.isPresent()){
            response.setSuccess(false);
            response.setMessage("Somthing went wrong please try again");

        }
        else {
            response.setSuccess(true);
        }
        return response;
    }
}
