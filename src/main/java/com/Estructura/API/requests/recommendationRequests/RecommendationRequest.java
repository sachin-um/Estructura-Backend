package com.Estructura.API.requests.recommendationRequests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationRequest {
    private String firstChoice;
    private List<String> secondChoice;
    private List<String> thirdChoice;
    private String district;
    private String price;
}
