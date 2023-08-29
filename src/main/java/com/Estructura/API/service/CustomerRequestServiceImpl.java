package com.Estructura.API.service;

import com.Estructura.API.model.*;
import com.Estructura.API.repository.CustomerRepository;
import com.Estructura.API.repository.CustomerRequestRepository;
import com.Estructura.API.repository.RequestTargetItemTypeRepository;
import com.Estructura.API.repository.RequestTargetProfessionalCategoryRepository;
import com.Estructura.API.requests.customerRequests.CustomerRequestRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
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
public class CustomerRequestServiceImpl implements CustomerRequestService{
    private final CustomerRequestRepository customerRequestRepository;
    private final RequestTargetProfessionalCategoryRepository categoryRepository;
    private final RequestTargetItemTypeRepository itemTypeRepository;
    private final CustomerService customerService;
    @Override
    public GenericAddOrUpdateResponse<CustomerRequestRequest> saveCustomerRequest(
        CustomerRequestRequest customerRequestRequest) throws IOException {
        GenericAddOrUpdateResponse<CustomerRequestRequest> response=
            new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(customerRequestRequest)){
            Optional<Customer> customer=
                customerService.findById(customerRequestRequest.getCustomerId());
            if (customer.isPresent()){
                CustomerRequest customerRequest=CustomerRequest.builder()
                    .description(customerRequestRequest.getDescription())
                    .shortDesc(customerRequestRequest.getShortDesc())
                    .maxPrice(customerRequestRequest.getMaxPrice())
                    .minPrice(customerRequestRequest.getMinPrice())
                    .customer(customer.get())
                    .createdBy(customerRequestRequest.getCustomerId())
                    .build();
                saveImagesAndDocuments(customerRequestRequest,customerRequest);
                CustomerRequest savedCustomerRequest=
                    customerRequestRepository.save(customerRequest);
                uploadImagesAndDocuments(customerRequestRequest,customerRequest);
                if (customerRequestRequest.getTargetCategories() != null){
                    customerRequestRequest.getTargetCategories().forEach(category->{
                        var theTargetCategory=
                            RequestTargetProfessionalCategory.builder().role(category)
                                .customerRequest(savedCustomerRequest)
                                .build();
                        categoryRepository.save(theTargetCategory);
                    });
                }
                if (customerRequestRequest.getTargetRetailCategories() !=null){
                    customerRequestRequest.getTargetRetailCategories().forEach(category->{
                        var theRetailCategory=
                            RequestTargetItemType.builder().retailItemType(category)
                                .customerRequest(savedCustomerRequest)
                                .build();
                        itemTypeRepository.save(theRetailCategory);
                    });
                }
                response.setSuccess(true);
                response.setId(customerRequest.getId());
            }else {
                response.addError("fatal", "Access denied");
            }
        }else {
            response.addError("fatal", "Bad Request");
        }
        return response;
    }

    @Override
    public ResponseEntity<List<CustomerRequest>> fetchCustomerRequestByRole(
        Role role) {
        return null;
    }

    private void saveImagesAndDocuments(CustomerRequestRequest customerRequestRequest,CustomerRequest customerRequest){
        int count=0;
        if (customerRequestRequest.getImages()!=null){
            for (MultipartFile file:customerRequestRequest.getImages()){
                if (!file.isEmpty() && file.getOriginalFilename() != null) {
                    String imageName = StringUtils.cleanPath(
                        file.getOriginalFilename());
                    if (count == 0) customerRequest.setImage1(imageName);
                    customerRequest.setImage1Name(FileUploadUtil.generateFileName(
                        imageName));//check the image count is less than 3
                    if (count == 1) customerRequest.setImage2(imageName);
                    customerRequest.setImage2Name(
                        FileUploadUtil.generateFileName(imageName));
                    if (count == 2) customerRequest.setImage3(imageName);
                    customerRequest.setImage3Name(
                        FileUploadUtil.generateFileName(imageName));
                    count++;
                }
            }
        }
        count=0;
        if (customerRequestRequest.getDocuments() != null) {
            for (MultipartFile document : customerRequestRequest.getDocuments()) {
                if (!document.isEmpty() &&
                    document.getOriginalFilename() != null) {
                    String documentName = StringUtils.cleanPath(
                        document.getOriginalFilename());
                    if (count == 0) customerRequest.setDocument1(documentName);
                    customerRequest.setDocument1Name(FileUploadUtil.generateFileName(
                        documentName));//check the image count is less than 3
                    if (count == 1) customerRequest.setDocument2(documentName);
                    customerRequest.setDocument2Name(
                        FileUploadUtil.generateFileName(documentName));
                    if (count == 2) customerRequest.setDocument3(documentName);
                    customerRequest.setDocument3Name(
                        FileUploadUtil.generateFileName(documentName));
                    count++;
                }
            }
        }
    }

    private void uploadImagesAndDocuments(CustomerRequestRequest customerRequestRequest,CustomerRequest customerRequest) throws IOException {
        int count=0;
        String uploadDir =
            "./files/customer-request-files/" + customerRequest.getCustomer().getId() + "/" +
            customerRequest.getId();
        if (customerRequestRequest.getImages() != null) {
            for (MultipartFile file : customerRequestRequest.getImages()) {
                if (!file.isEmpty()) {
                    if (count == 0) {
                        String fileName = StringUtils.cleanPath(
                            customerRequest.getImage1Name());
                        FileUploadUtil.saveFile(uploadDir, file, fileName);
                    }
                    if (count == 1) {
                        String fileName = StringUtils.cleanPath(
                            customerRequest.getImage2Name());
                        FileUploadUtil.saveFile(uploadDir, file, fileName);
                    }
                    if (count == 2) {
                        String fileName = StringUtils.cleanPath(
                            customerRequest.getImage3Name());
                        FileUploadUtil.saveFile(uploadDir, file, fileName);
                    }
                    count++;
                }
            }
        }
        if (customerRequestRequest.getDocuments() != null) {
            for (MultipartFile document : customerRequestRequest.getDocuments()) {
                if (!document.isEmpty()) {
                    if (count == 0) {
                        String fileName = StringUtils.cleanPath(
                            customerRequest.getDocument1Name());
                        FileUploadUtil.saveFile(uploadDir, document, fileName);
                    }
                    if (count == 1) {
                        String fileName = StringUtils.cleanPath(
                            customerRequest.getDocument2Name());
                        FileUploadUtil.saveFile(uploadDir, document, fileName);
                    }
                    if (count == 2) {
                        String fileName = StringUtils.cleanPath(
                            customerRequest.getDocument3Name());
                        FileUploadUtil.saveFile(uploadDir, document, fileName);
                    }
                    count++;
                }
            }
        }
    }
}
