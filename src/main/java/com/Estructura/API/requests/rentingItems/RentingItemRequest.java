package com.Estructura.API.requests.rentingItems;

import com.Estructura.API.model.RentingCategory;
import jakarta.persistence.Column;
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
public class RentingItemRequest {
    private String name;
    private String description;
    private Double price;
    private String scale;
    private Integer renterId;
    private RentingCategory category;

    private MultipartFile mainImage;
    private List<MultipartFile> extraImages;
}
