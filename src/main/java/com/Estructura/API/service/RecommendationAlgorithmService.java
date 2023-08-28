package com.Estructura.API.service;

import com.Estructura.API.model.*;
import com.Estructura.API.requests.recommendationRequests.RecommendationRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.responses.recommendationResponse.RecommendationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface RecommendationAlgorithmService {

    ResponseEntity<RecommendationResponse> recommend(@ModelAttribute RecommendationRequest recommendationRequest);
}
