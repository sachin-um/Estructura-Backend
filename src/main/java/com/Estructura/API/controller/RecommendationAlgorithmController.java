package com.Estructura.API.controller;

import com.Estructura.API.requests.recommendationRequests.RecommendationRequest;
import com.Estructura.API.responses.recommendationResponse.RecommendationResponse;
import com.Estructura.API.service.RecommendationAlgorithmService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/recommendation")
public class RecommendationAlgorithmController {
    private final RecommendationAlgorithmService recommendationAlgorithmService;

    @PostMapping("/recommend")
    public ResponseEntity<RecommendationResponse> recommend(@RequestBody RecommendationRequest recommendationRequest){
        return recommendationAlgorithmService.recommend(recommendationRequest);
    }

}

