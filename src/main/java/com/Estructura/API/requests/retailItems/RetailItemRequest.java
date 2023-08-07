package com.Estructura.API.requests.retailItems;

import com.Estructura.API.model.RetailItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetailItemRequest {
    private String name;
    private Double price;
    private String description;
    private Integer quantity;
    private Integer RetailStoreId;
    private RetailItemType retailItemType;
    private MultipartFile mainImage;
    private List<MultipartFile> extraImages;
}
