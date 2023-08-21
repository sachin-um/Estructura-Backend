package com.Estructura.API.repository;

import com.Estructura.API.model.RecommendationAlgorithm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RecommendationAlgorithmRepository extends JpaRepository<RecommendationAlgorithm,Long> {

}
