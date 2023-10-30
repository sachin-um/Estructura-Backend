package com.Estructura.API.service;

import com.Estructura.API.model.*;
import com.Estructura.API.requests.recommendationRequests.RecommendationRequest;
import com.Estructura.API.responses.recommendationResponse.RecommendationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.Estructura.API.model.RetailItemType.*;
import static com.Estructura.API.model.Role.*;

@Service
@AllArgsConstructor
public class RecommendationAlgorithmServiceImpl implements RecommendationAlgorithmService{

    private final ProfessionalService professionalService;
    private final RetailItemService retailItemService;
    //--------------------------------- weighting logic start -------------------------------------------------------//

    // Initialize professional weights
    final Role[] professionals = {ARCHITECT, INTERIORDESIGNER,
        CONSTRUCTIONCOMPANY,
            LANDSCAPEARCHITECT, PAINTER, MASONWORKER, CARPENTER};

    final Map<Role, Integer> ProfessionalWeights = Arrays.stream(professionals)
        .collect(HashMap::new, (map, profession) -> map.put(profession, 0), HashMap::putAll);

    // Initialize retail item weights
    final RetailItemType[] retailItems = {FURNITURE, HARDWARE,PAINT, BATHWARE, GARDENWARE, LIGHTING};

    final Map<RetailItemType, Integer> RetailItemWeights = Arrays.stream(retailItems)
        .collect(HashMap::new, (map, item) -> map.put(item, 0), HashMap::putAll);


    public static void modifyWeightP(Map<Role, Integer> map, Role key, int modifier) {
        int existingValue = map.getOrDefault(key, 0);
        map.put(key, existingValue + modifier);
    }
    public static void modifyWeightR(Map<RetailItemType, Integer> map, RetailItemType key, int modifier) {
        int existingValue = map.getOrDefault(key, 0);
        map.put(key, existingValue + modifier);
    }

    @Override
    public ResponseEntity<RecommendationResponse> recommend(RecommendationRequest recommendationRequest) {
        RecommendationResponse recommendationResponse=
            new RecommendationResponse();

        //todo: check with professionals and items what happens if any is null? there are no items!

        //all Professionals
        List<Professional> allProfessionals=
            professionalService.getAllProfessionals().getBody();

        //all Retail Items
        List<RetailItem> allRetailItems=
            retailItemService.getAllItems().getBody();

        final String firstChoice=recommendationRequest.getFirstChoice();
//        final String firstChoice= "design plans";
        final List<String> secondChoice=
            recommendationRequest.getSecondChoice();
//        final List<String> secondChoice= Arrays.asList("indoor design");
        final List<String> thirdChoice=recommendationRequest.getThirdChoice();
//        final List<String> thirdChoice= Arrays.asList();
        final String district=recommendationRequest.getDistrict();
//        final String district= "Puttalam";


        Map<Role, Integer> professionalWeights = getProfessionalWeights();
        Map<RetailItemType, Integer> retailItemWeights = getRetailItemWeights();

        System.out.println("Professional Weights: " + professionalWeights);
        System.out.println("Retail Item Weights: " + retailItemWeights);

        // Set the first stage values
        firstStageValues(firstChoice);

        System.out.println("Professional Weights: " + professionalWeights);
        System.out.println("Retail Item Weights: " + retailItemWeights);

        final List<String> nextChoicesArray = Stream.concat(secondChoice.stream(), thirdChoice.stream())
            .collect(Collectors.toList());
        secondStageValues(firstChoice, nextChoicesArray);

        System.out.println("Professional Weights: " + professionalWeights);
        System.out.println("Retail Item Weights: " + retailItemWeights);

        // Filter and sort the professionalWeights map
        List<Map.Entry<Role, Integer>> filteredProfessionalWeights = new ArrayList<>(professionalWeights.entrySet());
        filteredProfessionalWeights.removeIf(entry -> entry.getValue() <= 0);
        filteredProfessionalWeights.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Filter and sort the retailItemWeights map
        List<Map.Entry<RetailItemType, Integer>> filteredRetailItemWeights = new ArrayList<>(retailItemWeights.entrySet());
        filteredRetailItemWeights.removeIf(entry -> entry.getValue() <= 0);
        filteredRetailItemWeights.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        System.out.println("Filtered and Sorted Professional Weights: " + filteredProfessionalWeights);
        System.out.println("Filtered and Sorted Retail Item Weights: " + filteredRetailItemWeights);

        List<Role> filteredProfessionalTags = new ArrayList<>();
        for (Map.Entry<Role, Integer> entry : filteredProfessionalWeights) {
            filteredProfessionalTags.add(entry.getKey());
        }

        List<RetailItemType> filteredRetailItemTags = new ArrayList<>();
        for (Map.Entry<RetailItemType, Integer> entry : filteredRetailItemWeights) {
            filteredRetailItemTags.add(entry.getKey());
        }

        System.out.println("Filtered and Sorted Professional Tags: " + filteredProfessionalTags);
        System.out.println("Filtered and Sorted Retail Item Tags: " + filteredRetailItemTags);

        List<Professional> professionalsWithCorrectRole = new ArrayList<>();
        List<RetailItem>  RetailItemsWithCorrectType = new ArrayList<>();

        for (Professional professional : allProfessionals) {
            if (filteredProfessionalTags.contains(professional.getRole())) {
                professionalsWithCorrectRole.add(professional);
            }
        }


//        for (RetailItem retailitem : allRetailItems) {
//            if (filteredRetailItemTags.contains(retailitem.getRetailItemType())) {
//                RetailItemsWithCorrectType.add(retailitem);
//            }
//        }


        initializeGraph();

        var userSelectedDistrict = district.toLowerCase();

        List<String> adjacentDistrictsBasedOnUserSelectedDistrict = getAdjacentDistricts(userSelectedDistrict);
        System.out.println(adjacentDistrictsBasedOnUserSelectedDistrict);

        List<Professional> resultedProfessionals = getProfessionals(userSelectedDistrict, adjacentDistrictsBasedOnUserSelectedDistrict, professionalsWithCorrectRole);
//        List<RetailItem> resultedRetailStores = getRetailStores(userSelectedDistrict, adjacentDistrictsBasedOnUserSelectedDistrict, RetailItemsWithCorrectType);

        //todo: check with sort options and adjust

        // Sort professionals based on rating (highest to lowest)
        // resultedRetailStores.sort(Comparator.comparingDouble(product -> product.price));

        // Sort professionals based on rating (highest to lowest)
        // resultedProfessionals.sort((prof1, prof2) -> Double.compare(prof2.rating, prof1.rating));

        recommendationResponse.setProfessionals(resultedProfessionals);
//        recommendationResponse.setRetailItems(resultedRetailStores);
        recommendationResponse.setSuccess(true);
        return ResponseEntity.ok(recommendationResponse);
    }

    public void firstStageValues(String FirstChoice){

        switch (FirstChoice) {
            case "construction" -> {
                ProfessionalWeights.put(ARCHITECT, -100);
                ProfessionalWeights.put(INTERIORDESIGNER, -100);
                ProfessionalWeights.put(LANDSCAPEARCHITECT, -100);
                ProfessionalWeights.put(PAINTER, -100);
                ProfessionalWeights.put(CARPENTER, -100);
                RetailItemWeights.put(GARDENWARE, -100);
                RetailItemWeights.put(PAINT, -100);
            }
            case "design plans" -> {
                ProfessionalWeights.put(INTERIORDESIGNER, -100);
                ProfessionalWeights.put(CONSTRUCTIONCOMPANY, 100);
                ProfessionalWeights.put(MASONWORKER, -100);
                ProfessionalWeights.put(PAINTER, -100);
                ProfessionalWeights.put(CARPENTER, -100);
                RetailItemWeights.put(FURNITURE, -100);
                RetailItemWeights.put(HARDWARE, -100);
                RetailItemWeights.put(BATHWARE, -100);
                RetailItemWeights.put(GARDENWARE, -100);
                RetailItemWeights.put(LIGHTING, -100);
                RetailItemWeights.put(PAINT, -100);
            }
            case "landscaping" -> {
                ProfessionalWeights.put(ARCHITECT, -100);
                ProfessionalWeights.put(INTERIORDESIGNER, -100);
                ProfessionalWeights.put(CONSTRUCTIONCOMPANY, -100);
                RetailItemWeights.put(FURNITURE, -100);
                RetailItemWeights.put(BATHWARE, -100);
                RetailItemWeights.put(PAINT, -100);
            }
            case "painting" -> {
                modifyWeightP(ProfessionalWeights, ARCHITECT, -100);
                modifyWeightP(ProfessionalWeights, INTERIORDESIGNER, -100);
                modifyWeightP(ProfessionalWeights, CONSTRUCTIONCOMPANY, -100);
                modifyWeightP(ProfessionalWeights, LANDSCAPEARCHITECT, -100);
                modifyWeightP(ProfessionalWeights, MASONWORKER, -100);
                modifyWeightP(ProfessionalWeights, CARPENTER, -100);
                modifyWeightP(ProfessionalWeights, PAINTER, 30);
                modifyWeightR(RetailItemWeights, FURNITURE, -100);
                modifyWeightR(RetailItemWeights, PAINT, 30);
                modifyWeightR(RetailItemWeights, HARDWARE, -100);
                modifyWeightR(RetailItemWeights, BATHWARE, -100);
                modifyWeightR(RetailItemWeights, GARDENWARE, -100);
                modifyWeightR(RetailItemWeights, LIGHTING, -100);
            }
            case "remodeling" -> {
                ProfessionalWeights.put(CONSTRUCTIONCOMPANY, -100);
                RetailItemWeights.put(PAINT, -100);
            }
            case "interior design" -> {
                ProfessionalWeights.put(ARCHITECT, -100);
                ProfessionalWeights.put(CONSTRUCTIONCOMPANY, -100);
                ProfessionalWeights.put(LANDSCAPEARCHITECT, -100);
                ProfessionalWeights.put(MASONWORKER, -100);
                RetailItemWeights.put(HARDWARE, -100);
                RetailItemWeights.put(GARDENWARE, -100);
                RetailItemWeights.put(PAINT, -100);
            }
            case "woodwork" -> {
                ProfessionalWeights.put(ARCHITECT, -100);
                ProfessionalWeights.put(INTERIORDESIGNER, -100);
                ProfessionalWeights.put(CONSTRUCTIONCOMPANY, -100);
                ProfessionalWeights.put(LANDSCAPEARCHITECT, -100);
                ProfessionalWeights.put(MASONWORKER, -100);
                RetailItemWeights.put(BATHWARE, -100);
                RetailItemWeights.put(GARDENWARE, -100);
                RetailItemWeights.put(LIGHTING, -100);
                RetailItemWeights.put(HARDWARE, -100);
            }
            default -> {
            }
        }
    }

    public void secondStageValues(String firstChoice, List<String> secondChoice){
        switch (firstChoice) {
            case "construction" -> secondByConstruction(secondChoice);
            case "design plans" -> secondByDesignPlans(secondChoice);
            case "landscaping" -> secondByLandscaping(secondChoice);
            case "remodeling" -> secondByRemodeling(secondChoice);
            case "interior design" -> secondByInteriorDesign(secondChoice);
            case "woodwork" -> secondByWoodwork(secondChoice);
            default -> {
            }
        }
    }

    public void secondByConstruction(List<String> secondChoice){

        switch(secondChoice.get(0)){
            case "residence buildings" -> thirdByResidenceBuildings(secondChoice.subList(1, secondChoice.size()));
            case "commercial buildings" -> thirdByCommercialBuildings(secondChoice.get(1));
            case "industrial buildings" -> thirdByIndustrialBuildings(secondChoice.get(1));
            case "recreational buildings" -> thirdByRecreationalBuildings(secondChoice.get(1));
        }
    }

    public void thirdByResidenceBuildings(List<String> thirdChoice){
        if(thirdChoice.contains("all in one")){
            modifyWeightP(ProfessionalWeights, CONSTRUCTIONCOMPANY, 30);
            modifyWeightP(ProfessionalWeights, MASONWORKER, 20);

            modifyWeightR(RetailItemWeights, FURNITURE, 20);
            modifyWeightR(RetailItemWeights, HARDWARE, 20);
            modifyWeightR(RetailItemWeights, BATHWARE, 20);
            modifyWeightR(RetailItemWeights, LIGHTING, 20);
        }
        if(thirdChoice.contains("kitchen and dining") || thirdChoice.contains("bedroom") || thirdChoice.contains("living room") || thirdChoice.contains("office")){
            modifyWeightP(ProfessionalWeights, MASONWORKER, 30);
            modifyWeightP(ProfessionalWeights, CONSTRUCTIONCOMPANY, 20);

            modifyWeightR(RetailItemWeights, FURNITURE, 30);
            modifyWeightR(RetailItemWeights, HARDWARE, 20);
            modifyWeightR(RetailItemWeights, LIGHTING, 20);
        }
        if(thirdChoice.contains("garage")){
            modifyWeightP(ProfessionalWeights, MASONWORKER, 30);
            modifyWeightP(ProfessionalWeights, CONSTRUCTIONCOMPANY, 20);

            modifyWeightR(RetailItemWeights, HARDWARE, 30);
            modifyWeightR(RetailItemWeights, LIGHTING, 10);
        }
        if(thirdChoice.contains("bathroom")){
            modifyWeightP(ProfessionalWeights, MASONWORKER, 30);
            modifyWeightP(ProfessionalWeights, CONSTRUCTIONCOMPANY, 20);

            modifyWeightR(RetailItemWeights, BATHWARE, 30);
            modifyWeightR(RetailItemWeights, HARDWARE, 10);
        }
    }

    public void thirdByCommercialBuildings(String thirdChoice){
        if(thirdChoice.equals("sky scrapers") || thirdChoice.equals("restaurants and cafes") || thirdChoice.equals("hotels") || thirdChoice.equals("retail buildings") || thirdChoice.equals("office buildings")){
            refactorOnCommercialIndustrialAndRecreational();
        }
    }

    public void thirdByIndustrialBuildings(String thirdChoice){
        if(thirdChoice.equals("warehouses") || thirdChoice.equals("educational buildings") || thirdChoice.equals("healthcare") || thirdChoice.equals("religious and government buildings")){
            refactorOnCommercialIndustrialAndRecreational();
        }
    }

    public void thirdByRecreationalBuildings(String thirdChoice){
        if(thirdChoice.equals("entertainment and leisure") || thirdChoice.equals("swimming pools")){
            refactorOnCommercialIndustrialAndRecreational();
        }
    }

    private void refactorOnCommercialIndustrialAndRecreational() {
        modifyWeightP(ProfessionalWeights, CONSTRUCTIONCOMPANY, 30);
        modifyWeightP(ProfessionalWeights, MASONWORKER, 20);

        modifyWeightR(RetailItemWeights, HARDWARE, 30);
        modifyWeightR(RetailItemWeights, FURNITURE, 20);
        modifyWeightR(RetailItemWeights, BATHWARE, 20);
        modifyWeightR(RetailItemWeights, LIGHTING, 20);
    }

    public void secondByDesignPlans(List<String> secondChoice){
        if(secondChoice.contains("indoor design")){
            modifyWeightP(ProfessionalWeights, ARCHITECT, 30);
            modifyWeightP(ProfessionalWeights, LANDSCAPEARCHITECT, -100);
            restSecondByDesignPlans();
        } else if (secondChoice.contains("outdoor design")){
            modifyWeightP(ProfessionalWeights, ARCHITECT, -100);
            modifyWeightP(ProfessionalWeights, LANDSCAPEARCHITECT, 30);
            restSecondByDesignPlans();
        }
    }

    private void restSecondByDesignPlans() {
        modifyWeightP(ProfessionalWeights, MASONWORKER, -100);
        modifyWeightP(ProfessionalWeights, CARPENTER, -100);
        modifyWeightP(ProfessionalWeights, PAINTER, -100);
        modifyWeightP(ProfessionalWeights, INTERIORDESIGNER, -100);
        modifyWeightP(ProfessionalWeights, CONSTRUCTIONCOMPANY, -100);

        modifyWeightR(RetailItemWeights, FURNITURE, -100);
        modifyWeightR(RetailItemWeights, HARDWARE, -100);
        modifyWeightR(RetailItemWeights, BATHWARE, -100);
        modifyWeightR(RetailItemWeights, GARDENWARE, -100);
        modifyWeightR(RetailItemWeights, LIGHTING, -100);
    }

    public void secondByLandscaping(List<String> secondChoice){

        if(secondChoice.contains("gardening")){
            modifyWeightP(ProfessionalWeights, LANDSCAPEARCHITECT, 30);
            modifyWeightP(ProfessionalWeights, MASONWORKER, 10);
            modifyWeightP(ProfessionalWeights, PAINTER, 10);
            modifyWeightP(ProfessionalWeights, CARPENTER, 10);

            modifyWeightR(RetailItemWeights, GARDENWARE, 30);
        }
        if(secondChoice.contains("hardscaping")){
            modifyWeightP(ProfessionalWeights, LANDSCAPEARCHITECT, 30);
            modifyWeightP(ProfessionalWeights, MASONWORKER, 10);

            modifyWeightR(RetailItemWeights, HARDWARE, 30);
        }
        if(secondChoice.contains("water features")){
            modifyWeightP(ProfessionalWeights, MASONWORKER, 30);
        }
        if(secondChoice.contains("outdoor lighting")){
            modifyWeightP(ProfessionalWeights, LANDSCAPEARCHITECT, 30);
            modifyWeightP(ProfessionalWeights, MASONWORKER, 10);

            modifyWeightR(RetailItemWeights, LIGHTING, 30);
        }
        if(secondChoice.contains("landscape design")){
            modifyWeightP(ProfessionalWeights, LANDSCAPEARCHITECT, 30);

            modifyWeightR(RetailItemWeights, GARDENWARE, 20);
        }
        if(secondChoice.contains("maintenance")){
            modifyWeightP(ProfessionalWeights, MASONWORKER, 20);
            modifyWeightP(ProfessionalWeights, PAINTER, 20);
            modifyWeightP(ProfessionalWeights, CARPENTER, 20);

            modifyWeightR(RetailItemWeights, HARDWARE, 30);
        }
    }
    public void secondByRemodeling(List<String> secondChoice){
        //if the list contains indoor remodeling, case indoor remodel, else if the list contains outdoor remodeling, case outdoor remodel only a single choice cannot contain both indoor and outdoor remodelling in the list at the same time
        if(secondChoice.contains("indoor remodeling")){
            modifyWeightP(ProfessionalWeights, ARCHITECT, 30);
            modifyWeightP(ProfessionalWeights, INTERIORDESIGNER, 30);
            modifyWeightP(ProfessionalWeights, CONSTRUCTIONCOMPANY, -100);
            modifyWeightP(ProfessionalWeights, LANDSCAPEARCHITECT, -100);
            modifyWeightP(ProfessionalWeights, MASONWORKER, 20);
            modifyWeightP(ProfessionalWeights, CARPENTER, 10);
            modifyWeightP(ProfessionalWeights, PAINTER, 10);

            modifyWeightR(RetailItemWeights, FURNITURE, 30);
            modifyWeightR(RetailItemWeights, HARDWARE, 20);
            modifyWeightR(RetailItemWeights, BATHWARE, 10);
            modifyWeightR(RetailItemWeights, GARDENWARE, -100);
            modifyWeightR(RetailItemWeights, LIGHTING, 10);
        }
        else if(secondChoice.contains("outdoor remodeling")){

            modifyWeightP(ProfessionalWeights, ARCHITECT, -100);
            modifyWeightP(ProfessionalWeights, INTERIORDESIGNER, -100);
            modifyWeightP(ProfessionalWeights, CONSTRUCTIONCOMPANY, -100);
            modifyWeightP(ProfessionalWeights, LANDSCAPEARCHITECT, 30);
            modifyWeightP(ProfessionalWeights, MASONWORKER, 30);
            modifyWeightP(ProfessionalWeights, CARPENTER, 10);
            modifyWeightP(ProfessionalWeights, PAINTER, 10);

            modifyWeightR(RetailItemWeights, FURNITURE, -100);
            modifyWeightR(RetailItemWeights, HARDWARE,30);
            modifyWeightR(RetailItemWeights, BATHWARE, -100);
            modifyWeightR(RetailItemWeights, GARDENWARE, 20);
            modifyWeightR(RetailItemWeights, LIGHTING, -100);
        }
    }
    public void secondByInteriorDesign(List<String> secondChoice){

        if(secondChoice.contains("space planning")){
            modifyWeightP(ProfessionalWeights, INTERIORDESIGNER, 30);

            modifyWeightR(RetailItemWeights, FURNITURE, 30);
            modifyWeightR(RetailItemWeights, BATHWARE, 20);
            modifyWeightR(RetailItemWeights, LIGHTING, 20);
        }
        if(secondChoice.contains("furniture and furnishings")){
            modifyWeightP(ProfessionalWeights, INTERIORDESIGNER, 30);
            modifyWeightP(ProfessionalWeights, CARPENTER, 20);
            modifyWeightP(ProfessionalWeights, PAINTER, 20);

            modifyWeightR(RetailItemWeights, FURNITURE, 30);
        }
        if(secondChoice.contains("color scheme and paint selection")){
            modifyWeightP(ProfessionalWeights, INTERIORDESIGNER, 30);
            modifyWeightP(ProfessionalWeights, PAINTER, 20);
        }
        if(secondChoice.contains("lighting design")){
            modifyWeightP(ProfessionalWeights, INTERIORDESIGNER, 30);

            modifyWeightR(RetailItemWeights, LIGHTING, 30);
        }
        if(secondChoice.contains("flooring and wall treatments")){
            modifyWeightP(ProfessionalWeights, INTERIORDESIGNER, 30);
            modifyWeightP(ProfessionalWeights, PAINTER, 20);
            modifyWeightP(ProfessionalWeights, CARPENTER, 20);
        }
        if(secondChoice.contains("accessories and decorative elements")){
            modifyWeightP(ProfessionalWeights, INTERIORDESIGNER, 30);

            modifyWeightR(RetailItemWeights, FURNITURE, 30);
            modifyWeightR(RetailItemWeights, BATHWARE, 20);
            modifyWeightR(RetailItemWeights, LIGHTING, 20);
        }

    }
    public void secondByWoodwork(List<String> secondChoice){

        if(secondChoice.contains("carpentry") || secondChoice.contains("cabinetry") || secondChoice.contains("woodturning") || secondChoice.contains("wood carving") || secondChoice.contains("wood flooring") || secondChoice.contains("restoration and repair")){
            modifyWeightP(ProfessionalWeights, CARPENTER, 30);

            modifyWeightR(RetailItemWeights, FURNITURE, 30);
        }
        if(secondChoice.contains("wood finishing")){
            modifyWeightP(ProfessionalWeights, PAINTER, 30);

            modifyWeightR(RetailItemWeights, PAINT, 30);
        }

    }

    //--------------------------------- weighting logic complete -------------------------------------------------------//

    //--------------------------------- district mapping logic start ---------------------------------------------------//


    private final Map<String, List<String>> districtAdjacencyMap = new HashMap<>();

    public void addEdge(String district1, String district2) {
        districtAdjacencyMap.computeIfAbsent(district1, k -> new ArrayList<>()).add(district2);
        districtAdjacencyMap.computeIfAbsent(district2, k -> new ArrayList<>()).add(district1);
    }

    public List<String> getAdjacentDistricts(String district) {
        return districtAdjacencyMap.getOrDefault(district, new ArrayList<>());
    }

    //initialize the district graph
    public void initializeGraph(){
        addEdge("ampara", "badulla");
        addEdge("ampara", "hambanthota");
        addEdge("ampara", "monaragala");
        addEdge("anuradhapura", "kurunegala");
        addEdge("anuradhapura", "matale");
        addEdge("anuradhapura", "polonnaruwa");
        addEdge("anuradhapura", "puttalam");
        addEdge("anuradhapura", "trincomalee");
        addEdge("badulla", "monaragala");
        addEdge("badulla", "rathnapura");
        addEdge("batticaloa", "ampara");
        addEdge("colombo", "kalutara");
        addEdge("colombo", "rathnapura");
        addEdge("galle", "kalutara");
        addEdge("galle", "matara");
        addEdge("galle", "rathnapura");
        addEdge("gampaha", "colombo");
        addEdge("hambanthota", "matara");
        addEdge("hambanthota", "monaragala");
        addEdge("hambanthota", "rathnapura");
        addEdge("jaffna", "kilinochchi");
        addEdge("kalutara", "rathnapura");
        addEdge("kandy", "badulla");
        addEdge("kandy", "kegalle");
        addEdge("kandy", "nuwara-eliya");
        addEdge("kegalle", "colombo");
        addEdge("kegalle", "gampaha");
        addEdge("kegalle", "nuwara-eliya");
        addEdge("kegalle", "rathnapura");
        addEdge("kilinochchi", "mannar");
        addEdge("kilinochchi", "mullaitivu");
        addEdge("kurunegala", "gampaha");
        addEdge("kurunegala", "kandy");
        addEdge("kurunegala", "kegalle");
        addEdge("kurunegala", "matale");
        addEdge("mannar", "anuradhapura");
        addEdge("mannar", "puttalam");
        addEdge("mannar", "vavuniya");
        addEdge("matale", "ampara");
        addEdge("matale", "badulla");
        addEdge("matale", "kandy");
        addEdge("matara", "rathnapura");
        addEdge("monaragala", "rathnapura");
        addEdge("mullaitivu", "mannar");
        addEdge("mullaitivu", "trincomalee");
        addEdge("mullaitivu", "vavuniya");
        addEdge("nuwara-eliya", "badulla");
        addEdge("nuwara-eliya", "rathnapura");
        addEdge("polonnaruwa", "ampara");
        addEdge("polonnaruwa", "batticaloa");
        addEdge("polonnaruwa", "matale");
        addEdge("puttalam", "gampaha");
        addEdge("puttalam", "kurunegala");
        addEdge("trincomalee", "batticaloa");
        addEdge("trincomalee", "polonnaruwa");
        addEdge("vavuniya", "anuradhapura");
        addEdge("vavuniya", "trincomalee");
    }

    public static List<Professional> getProfessionals(String userDistrict,
        List<String> selectedDistricts, List<Professional> filteredProfessionals) {

            List<Professional> results_professional = new ArrayList<>();
            //go through the filteredProfessionals list and find the professionals who are in the userDistrict
            //if there are no professionals in the userDistrict, find the professionals in the adjacent districts
            //if there are no professionals in the adjacent districts, return no results found
            //if there are professionals in the userDistrict, return the list of professionals
            //if there are professionals in the adjacent districts, return the list of professionals

            List<Professional> professionalsInUserDistrict = new ArrayList<>();
            List<Professional> professionalsInAdjacentDistricts = new ArrayList<>();

            for (Professional professional : filteredProfessionals) {
                // Check if the professional is in the userDistrict
                if (professional.getDistrict()
                                .toLowerCase()
                                .equals(userDistrict)) {
                    professionalsInUserDistrict.add(professional);
                } else {
                    // Check if the professional is in any of the adjacent districts
                    boolean foundInAdjacent = false;
                    for (String adjacentDistrict : selectedDistricts) {
                        if (professional.getDistrict()
                                        .toLowerCase()
                                        .equals(adjacentDistrict)) {
                            foundInAdjacent = true;
                            break;
                        }
                    }
                    if (foundInAdjacent) {
                        professionalsInAdjacentDistricts.add(professional);
                    }
                }
            }

            if (!professionalsInUserDistrict.isEmpty()) {
                results_professional.addAll(professionalsInUserDistrict);
            } else if (!professionalsInAdjacentDistricts.isEmpty()) {
                results_professional.addAll(professionalsInAdjacentDistricts);
            } else {
                results_professional.add(null);
            }

        return results_professional;
    }

    public static List<RetailItem> getRetailStores(String userDistrict, List<String> selectedDistricts, List<RetailItem> filteredRetailStores) {

            List<RetailItem> results_retail = new ArrayList<>();

            //go through the filteredRetailStores list and find the retail stores and items which are in the userDistrict
            //if there are no retail stores and items in the userDistrict, find the retail stores and items in the adjacent districts
            //if there are no retail stores and items in the adjacent districts, return no results found
            //if there are retail stores and items  in the userDistrict, return the list of items
            //if there are retail stores and items in the adjacent districts, return the list of items

            List<RetailItem> retailItemInUserDistrict = new ArrayList<>();
            List<RetailItem> retailItemInAdjacentDistricts = new ArrayList<>();

            for (RetailItem retailItem : filteredRetailStores) {
                // Check if the professional is in the userDistrict
                if (true /* Check if the professional is in userDistrict */) {
                    retailItemInUserDistrict.add(retailItem);
                } else {
                    // Check if the professional is in any of the adjacent districts
                    boolean foundInAdjacent = false;
                    for (String adjacentDistrict : selectedDistricts) {
                        if ( true /* Check if the professional is in adjacentDistrict */) {
                            foundInAdjacent = true;
                            break;
                        }
                    }
                    if (foundInAdjacent) {
                        retailItemInAdjacentDistricts.add(retailItem);
                    }
                }
            }

            if (!retailItemInUserDistrict.isEmpty()) {
                results_retail.addAll(retailItemInUserDistrict);
            } else if (!retailItemInAdjacentDistricts.isEmpty()) {
                results_retail.addAll(retailItemInAdjacentDistricts);
            } else {
                results_retail.add(null);
            }

            return results_retail;
    }



    //--------------------------------- district mapping logic end ---------------------------------------------------//

    public Map<Role, Integer> getProfessionalWeights() {
        return ProfessionalWeights;
    }

    public Map<RetailItemType, Integer> getRetailItemWeights() {
        return RetailItemWeights;
    }

}
