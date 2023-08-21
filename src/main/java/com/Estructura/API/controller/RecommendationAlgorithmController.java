package com.Estructura.API.controller;

import com.Estructura.API.service.RecommendationAlgorithmService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/recommendationAlgorithm")
public class RecommendationAlgorithmController {

    private final RecommendationAlgorithmService recommendationAlgorithmService;

}

