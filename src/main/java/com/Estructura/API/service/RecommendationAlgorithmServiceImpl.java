package com.Estructura.API.service;

import com.Estructura.API.model.Professional;
import com.Estructura.API.model.RetailItem;
import com.Estructura.API.model.RetailStore;
import com.Estructura.API.model.Role;
import com.Estructura.API.requests.recommendationRequests.RecommendationRequest;
import com.Estructura.API.responses.recommendationResponse.RecommendationResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class RecommendationAlgorithmServiceImpl implements RecommendationAlgorithmService{

    private final ProfessionalService professionalService;
    private final RetailItemService retailItemService;
    //--------------------------------- weighting logic start -------------------------------------------------------//


    // Initialize the map with professionals and retail items
//    private final Map<String, Integer> ProfessionalWeights = new HashMap<>();
//    private final Map<String, Integer> RetailItemWeights = new HashMap<>();

    final String[] professionals = {"architect", "interior designer",
        "construction company",
            "landscape architect", "painter", "home builder", "carpenter"};
//        for (String professional : professionals) {
//        ProfessionalWeights.put(professional, 0);
//    }
    final Map<String, Integer> ProfessionalWeights = Arrays.stream(professionals)
        .collect(HashMap::new, (map, profession) -> map.put(profession, 0), HashMap::putAll);

    // Initialize retail item weights
    final String[] retailItems = {"furniture", "hardware","hardware-paint", "bathware", "gardenware", "lighting"};
//        for (String retailItem : retailItems) {
//        RetailItemWeights.put(retailItem, 0);
//    }
    final Map<String, Integer> RetailItemWeights = Arrays.stream(retailItems)
        .collect(HashMap::new, (map, item) -> map.put(item, 0), HashMap::putAll);



    public static void modifyWeight(Map<String, Integer> map, String key, int modifier) {
        int existingValue = map.getOrDefault(key, 0);
        map.put(key, existingValue + modifier);
    }

    @Override
    public ResponseEntity<RecommendationResponse> recommend(RecommendationRequest recommendationRequest) {
        //all Professionals
        List<Professional> allProfessionals=
            professionalService.getAllProfessionals().getBody();
        //all Retail Items
        List<RetailItem> allRetailItems=
            retailItemService.getAllItems().getBody();
        final String firstChoice=recommendationRequest.getFirstChoice();
        final List<String> secoundChoice=
            recommendationRequest.getSecondChoice();
        final List<String> thirdChoice=recommendationRequest.getThirdChoice();

        System.out.println(allProfessionals);
        System.out.println(allRetailItems);

        Map<String, Integer> professionalWeights = getProfessionalWeights();
        Map<String, Integer> retailItemWeights = getRetailItemWeights();

        System.out.println("Professional Weights: " + professionalWeights);
        System.out.println("Retail Item Weights: " + retailItemWeights);

        // Set the first stage values
        firstStageValues("construction");

        System.out.println("Professional Weights: " + professionalWeights);
        System.out.println("Retail Item Weights: " + retailItemWeights);

        secondStageValues("construction", Arrays.asList("commercial buildings", "sky scrapers"));

        // Print the initialized maps
        System.out.println("Professional Weights: " + professionalWeights);
        System.out.println("Retail Item Weights: " + retailItemWeights);

        // Filter and sort the professionalWeights map
        List<Map.Entry<String, Integer>> filteredProfessionalWeights = new ArrayList<>(professionalWeights.entrySet());
        filteredProfessionalWeights.removeIf(entry -> entry.getValue() <= 0);
        filteredProfessionalWeights.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Filter and sort the retailItemWeights map
        List<Map.Entry<String, Integer>> filteredRetailItemWeights = new ArrayList<>(retailItemWeights.entrySet());
        filteredRetailItemWeights.removeIf(entry -> entry.getValue() <= 0);
        filteredRetailItemWeights.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        System.out.println("Filtered and Sorted Professional Weights: " + filteredProfessionalWeights);
        System.out.println("Filtered and Sorted Retail Item Weights: " + filteredRetailItemWeights);

        List<String> filteredProfessionalTags = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : filteredProfessionalWeights) {
            filteredProfessionalTags.add(entry.getKey());
        }

        List<String> filteredRetailItemTags = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : filteredRetailItemWeights) {
            filteredRetailItemTags.add(entry.getKey());
        }

        System.out.println("Filtered and Sorted Professional Tags: " + filteredProfessionalTags);
        System.out.println("Filtered and Sorted Retail Item Tags: " + filteredRetailItemTags);

        List<Professional> tempvar1 = new ArrayList<>();
        List<RetailItem>  tempvar2 = new ArrayList<>();

        for (Professional professional : allProfessionals) {
            if(filteredProfessionalTags.contains(professional.getRole().toString().toLowerCase())){
                tempvar1.add(professional);
            }
        }

        for (RetailItem retailitem : allRetailItems){
            if(filteredRetailItemTags.contains(retailitem.getRetailItemType().toString().toLowerCase())){
                tempvar2.add(retailitem);
            }
        }

        System.out.println(tempvar1);
        System.out.println(tempvar2);


        //todo: get the professionals and retail stores based on the filteredprofrssionaltags and filteredretailitemtags
        //todo: assign them into two different variables and use those variables in the below method to get resultedProfessionals and resultedRetailstores

        initializeGraph();

        //todo: get the user selected district and assign it to userSelectedDistrict variable
        var userSelectedDistrict = "ampara";

        List<String> adjacentDistrictsBasedOnUserSelectedDistrict = getAdjacentDistricts(userSelectedDistrict);
        System.out.println(adjacentDistrictsBasedOnUserSelectedDistrict);

        List<String> resultedProfessionals =
            getProfessionalsOrRetailStores(userSelectedDistrict,
                adjacentDistrictsBasedOnUserSelectedDistrict, "Professional",
                tempvar1, null);
        List<String> resultedRetailstores =
            getProfessionalsOrRetailStores(userSelectedDistrict,
                adjacentDistrictsBasedOnUserSelectedDistrict, "retailstore",
                null, tempvar2);

        // Sort professionals based on rating (highest to lowest)
        // resultedRetailstores.sort(Comparator.comparingDouble(product -> product.price));

        // Sort professionals based on rating (highest to lowest)
        // resultedProfessionals.sort((prof1, prof2) -> Double.compare(prof2.rating, prof1.rating));
        return null;
    }
//    public RecommendationAlgorithmServiceImpl() {
//        // Initialize professional weights
//        String[] professionals = {"architect", "interior designer", "construction company",
//                "landscape architect", "painter", "home builder", "carpenter"};
//        for (String professional : professionals) {
//            ProfessionalWeights.put(professional, 0);
//        }
//
//        // Initialize retail item weights
//        String[] retailItems = {"furniture", "hardware","hardware-paint", "bathware", "gardenware", "lighting"};
//        for (String retailItem : retailItems) {
//            RetailItemWeights.put(retailItem, 0);
//        }
//    }

    //begining
    public void firstStageValues(String FirstChoice){

        switch (FirstChoice) {
            case "construction" -> {
                ProfessionalWeights.put("architect", -100);
                ProfessionalWeights.put("interior designer", -100);
                ProfessionalWeights.put("landscape architect", -100);
                ProfessionalWeights.put("painter", -100);
                ProfessionalWeights.put("carpenter", -100);
                RetailItemWeights.put("gardenware", -100);
                RetailItemWeights.put("hardware-paint", -100);
            }
            case "design plans" -> {
                ProfessionalWeights.put("interior designer", -100);
                ProfessionalWeights.put("construction company", 100);
                ProfessionalWeights.put("home builder", -100);
                ProfessionalWeights.put("painter", -100);
                ProfessionalWeights.put("carpenter", -100);
                RetailItemWeights.put("furniture", -100);
                RetailItemWeights.put("hardware", -100);
                RetailItemWeights.put("bathware", -100);
                RetailItemWeights.put("gardenware", -100);
                RetailItemWeights.put("lighting", -100);
                RetailItemWeights.put("hardware-paint", -100);
            }
            case "landscaping" -> {
                ProfessionalWeights.put("architect", -100);
                ProfessionalWeights.put("interior designer", -100);
                ProfessionalWeights.put("construction company", -100);
                RetailItemWeights.put("furniture", -100);
                RetailItemWeights.put("bathware", -100);
                RetailItemWeights.put("hardware-paint", -100);
            }
            case "painting" -> {
                modifyWeight(ProfessionalWeights, "architect", -100);
                modifyWeight(ProfessionalWeights, "interior designer", -100);
                modifyWeight(ProfessionalWeights, "construction company", -100);
                modifyWeight(ProfessionalWeights, "landscape architect", -100);
                modifyWeight(ProfessionalWeights, "home builder", -100);
                modifyWeight(ProfessionalWeights, "carpenter", -100);
                modifyWeight(ProfessionalWeights, "painter", 30);
                modifyWeight(RetailItemWeights, "furniture", -100);
                modifyWeight(RetailItemWeights, "hardware-paint", 30);
                modifyWeight(RetailItemWeights, "hardware", -100);
                modifyWeight(RetailItemWeights, "bathware", -100);
                modifyWeight(RetailItemWeights, "gardenware", -100);
                modifyWeight(RetailItemWeights, "lighting", -100);
            }
            case "remodeling" -> {
                ProfessionalWeights.put("construction company", -100);
                RetailItemWeights.put("hardware-paint", -100);
            }
            case "interior design" -> {
                ProfessionalWeights.put("architect", -100);
                ProfessionalWeights.put("construction company", -100);
                ProfessionalWeights.put("landscape architect", -100);
                ProfessionalWeights.put("home builder", -100);
                RetailItemWeights.put("hardware", -100);
                RetailItemWeights.put("gardenware", -100);
                RetailItemWeights.put("hardware-paint", -100);
            }
            case "woodwork" -> {
                ProfessionalWeights.put("architect", -100);
                ProfessionalWeights.put("interior designer", -100);
                ProfessionalWeights.put("construction company", -100);
                ProfessionalWeights.put("landscape architect", -100);
                ProfessionalWeights.put("home builder", -100);
                RetailItemWeights.put("bathware", -100);
                RetailItemWeights.put("gardenware", -100);
                RetailItemWeights.put("lighting", -100);
                RetailItemWeights.put("hardware", -100);
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
            modifyWeight(ProfessionalWeights, "construction company", 30);
            modifyWeight(ProfessionalWeights, "home builder", 20);

            modifyWeight(RetailItemWeights, "furniture", 20);
            modifyWeight(RetailItemWeights, "hardware", 20);
            modifyWeight(RetailItemWeights, "bathware", 20);
            modifyWeight(RetailItemWeights, "lighting", 20);
        }
        if(thirdChoice.contains("kitchen and dining") || thirdChoice.contains("bedroom") || thirdChoice.contains("living room") || thirdChoice.contains("office")){
            modifyWeight(ProfessionalWeights, "home builder", 30);
            modifyWeight(ProfessionalWeights, "construction company", 20);

            modifyWeight(RetailItemWeights, "furniture", 30);
            modifyWeight(RetailItemWeights, "hardware", 20);
            modifyWeight(RetailItemWeights, "lighting", 20);
        }
        if(thirdChoice.contains("garage")){
            modifyWeight(ProfessionalWeights, "home builder", 30);
            modifyWeight(ProfessionalWeights, "construction company", 20);

            modifyWeight(RetailItemWeights, "hardware", 30);
            modifyWeight(RetailItemWeights, "lighting", 10);
        }
        if(thirdChoice.contains("bathroom")){
            modifyWeight(ProfessionalWeights, "home builder", 30);
            modifyWeight(ProfessionalWeights, "construction company", 20);

            modifyWeight(RetailItemWeights, "bathware", 30);
            modifyWeight(RetailItemWeights, "hardware", 10);
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
        modifyWeight(ProfessionalWeights, "construction company", 30);
        modifyWeight(ProfessionalWeights, "home builder", 20);

        modifyWeight(RetailItemWeights, "hardware", 30);
        modifyWeight(RetailItemWeights, "furniture", 20);
        modifyWeight(RetailItemWeights, "bathware", 20);
        modifyWeight(RetailItemWeights, "lighting", 20);
    }

    public void secondByDesignPlans(List<String> secondChoice){
        if(secondChoice.contains("indoor design")){
            modifyWeight(ProfessionalWeights, "architect", 30);
            modifyWeight(ProfessionalWeights, "landscape architect", -100);
            restSecondByDesignPlans();
        } else if (secondChoice.contains("outdoor design")){
            modifyWeight(ProfessionalWeights, "architect", -100);
            modifyWeight(ProfessionalWeights, "landscape architect", 30);
            restSecondByDesignPlans();
        }
    }

    private void restSecondByDesignPlans() {
        modifyWeight(ProfessionalWeights, "home builder", -100);
        modifyWeight(ProfessionalWeights, "carpenter", -100);
        modifyWeight(ProfessionalWeights, "painter", -100);
        modifyWeight(ProfessionalWeights, "interior designer", -100);
        modifyWeight(ProfessionalWeights, "construction company", -100);

        modifyWeight(RetailItemWeights, "furniture", -100);
        modifyWeight(RetailItemWeights, "hardware", -100);
        modifyWeight(RetailItemWeights, "bathware", -100);
        modifyWeight(RetailItemWeights, "gardenware", -100);
        modifyWeight(RetailItemWeights, "lighting", -100);
    }

    public void secondByLandscaping(List<String> secondChoice){

        if(secondChoice.contains("gardening")){
            modifyWeight(ProfessionalWeights, "landscape architect", 30);
            modifyWeight(ProfessionalWeights, "home builder", 10);
            modifyWeight(ProfessionalWeights, "painter", 10);
            modifyWeight(ProfessionalWeights, "carpenter", 10);

            modifyWeight(RetailItemWeights, "gardenware", 30);
        }
        if(secondChoice.contains("hardscaping")){
            modifyWeight(ProfessionalWeights, "landscape architect", 30);
            modifyWeight(ProfessionalWeights, "home builder", 10);

            modifyWeight(RetailItemWeights, "hardware", 30);
        }
        if(secondChoice.contains("water features")){
            modifyWeight(ProfessionalWeights, "home builder", 30);
        }
        if(secondChoice.contains("outdoor lighting")){
            modifyWeight(ProfessionalWeights, "landscape architect", 30);
            modifyWeight(ProfessionalWeights, "home builder", 10);

            modifyWeight(RetailItemWeights, "lighting", 30);
        }
        if(secondChoice.contains("landscape design")){
            modifyWeight(ProfessionalWeights, "landscape architect", 30);

            modifyWeight(RetailItemWeights, "gardenware", 20);
        }
        if(secondChoice.contains("maintenance")){
            modifyWeight(ProfessionalWeights, "home builder", 20);
            modifyWeight(ProfessionalWeights, "painter", 20);
            modifyWeight(ProfessionalWeights, "carpenter", 20);

            modifyWeight(RetailItemWeights, "hardware", 30);
        }
    }
    public void secondByRemodeling(List<String> secondChoice){
        //if the list contains indoor remodeling, case indoor remodel, else if the list contains outdoor remodeling, case outdoor remodel only a single choice cannot contain both indoor and outdoor remodelling in the list at the same time
        if(secondChoice.contains("indoor remodeling")){
            modifyWeight(ProfessionalWeights, "architect", 30);
            modifyWeight(ProfessionalWeights, "interior designer", 30);
            modifyWeight(ProfessionalWeights, "construction company", -100);
            modifyWeight(ProfessionalWeights, "landscape architect", -100);
            modifyWeight(ProfessionalWeights, "home builder", 20);
            modifyWeight(ProfessionalWeights, "carpenter", 10);
            modifyWeight(ProfessionalWeights, "painter", 10);

            modifyWeight(RetailItemWeights, "furniture", 30);
            modifyWeight(RetailItemWeights, "hardware", 20);
            modifyWeight(RetailItemWeights, "bathware", 10);
            modifyWeight(RetailItemWeights, "gardenware", -100);
            modifyWeight(RetailItemWeights, "lighting", 10);
        }
        else if(secondChoice.contains("outdoor remodeling")){

            modifyWeight(ProfessionalWeights, "architect", -100);
            modifyWeight(ProfessionalWeights, "interior designer", -100);
            modifyWeight(ProfessionalWeights, "construction company", -100);
            modifyWeight(ProfessionalWeights, "landscape architect", 30);
            modifyWeight(ProfessionalWeights, "home builder", 30);
            modifyWeight(ProfessionalWeights, "carpenter", 10);
            modifyWeight(ProfessionalWeights, "painter", 10);

            modifyWeight(RetailItemWeights, "furniture", -100);
            modifyWeight(RetailItemWeights, "hardware",30);
            modifyWeight(RetailItemWeights, "bathware", -100);
            modifyWeight(RetailItemWeights, "gardenware", 20);
            modifyWeight(RetailItemWeights, "lighting", -100);
        }
    }
    public void secondByInteriorDesign(List<String> secondChoice){

        if(secondChoice.contains("space planning")){
            modifyWeight(ProfessionalWeights, "interior designer", 30);

            modifyWeight(RetailItemWeights, "furniture", 30);
            modifyWeight(RetailItemWeights, "bathware", 20);
            modifyWeight(RetailItemWeights, "lighting", 20);
        }
        if(secondChoice.contains("furniture and furnishings")){
            modifyWeight(ProfessionalWeights, "interior designer", 30);
            modifyWeight(ProfessionalWeights, "carpenter", 20);
            modifyWeight(ProfessionalWeights, "painter", 20);

            modifyWeight(RetailItemWeights, "furniture", 30);
        }
        if(secondChoice.contains("color scheme and paint selection")){
            modifyWeight(ProfessionalWeights, "interior designer", 30);
            modifyWeight(ProfessionalWeights, "painter", 20);
        }
        if(secondChoice.contains("lighting design")){
            modifyWeight(ProfessionalWeights, "interior designer", 30);

            modifyWeight(RetailItemWeights, "lighting", 30);
        }
        if(secondChoice.contains("flooring and wall treatments")){
            modifyWeight(ProfessionalWeights, "interior designer", 30);
            modifyWeight(ProfessionalWeights, "painter", 20);
            modifyWeight(ProfessionalWeights, "carpenter", 20);
        }
        if(secondChoice.contains("accessories and decorative elements")){
            modifyWeight(ProfessionalWeights, "interior designer", 30);

            modifyWeight(RetailItemWeights, "furniture", 30);
            modifyWeight(RetailItemWeights, "bathware", 20);
            modifyWeight(RetailItemWeights, "lighting", 20);
        }

    }
    public void secondByWoodwork(List<String> secondChoice){

        if(secondChoice.contains("carpentry") || secondChoice.contains("cabinetry") || secondChoice.contains("woodturning") || secondChoice.contains("wood carving") || secondChoice.contains("wood flooring") || secondChoice.contains("restoration and repair")){
            modifyWeight(ProfessionalWeights, "carpenter", 30);

            modifyWeight(RetailItemWeights, "furniture", 30);
        }
        if(secondChoice.contains("wood finishing")){
            modifyWeight(ProfessionalWeights, "painter", 30);

            modifyWeight(RetailItemWeights, "hardware-paint", 30);
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

    public static List<String> getProfessionalsOrRetailStores(String userDistrict,
        List<String> selectedDistricts, String userType, List<Professional> filteredProfessionals, List<RetailItem> filteredRetailStores) {
        List<Professional> results_professional = new ArrayList<>();
        List<RetailItem> results_retail = new ArrayList<>();

        if (userType.equals("professional")) {
            //go through the filteredProfessionalsOrRetailStores list and find the professionals who are in the userDistrict
            //if there are no professionals in the userDistrict, find the professionals in the adjacent districts
            //if there are no professionals in the adjacent districts, return no results found
            //if there are professionals in the userDistrict, return the list of professionals
            //if there are professionals in the adjacent districts, return the list of professionals

            List<Professional> professionalsInUserDistrict = new ArrayList<>();
            List<Professional> professionalsInAdjacentDistricts = new ArrayList<>();

            for (Professional professional : filteredProfessionals) {
                // Check if the professional is in the userDistrict
                if (true /* Check if the professional is in userDistrict */) {
                    professionalsInUserDistrict.add(professional);
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
            System.out.println(results_professional);

        } else if (userType.equals("retailstore")) {
            // Similar logic for retail stores
            //go through the filteredProfessionalsOrRetailStores list and find the retail stores who are in the userDistrict
            //if there are no retail stores in the userDistrict, find the retail stores in the adjacent districts
            //if there are no retail stores in the adjacent districts, return no results found
            //if there are retail stores in the userDistrict, return the list of retail stores
            //if there are retail stores in the adjacent districts, return the list of retail stores
            System.out.println(results_retail);
        }
        return null;
    }



    //--------------------------------- district mapping logic end ---------------------------------------------------//

    public Map<String, Integer> getProfessionalWeights() {
        return ProfessionalWeights;
    }

    public Map<String, Integer> getRetailItemWeights() {
        return RetailItemWeights;
    }



//    public static void main(String[] args) {
//        // Initialize the recommendation algorithm
//        RecommendationAlgorithmServiceImpl initializer = new RecommendationAlgorithmServiceImpl();
//
//        Map<String, Integer> professionalWeights = initializer.getProfessionalWeights();
//        Map<String, Integer> retailItemWeights = initializer.getRetailItemWeights();
//
//        System.out.println("Professional Weights: " + professionalWeights);
//        System.out.println("Retail Item Weights: " + retailItemWeights);
//
//        // Set the first stage values
//        initializer.firstStageValues("construction");
//
//        System.out.println("Professional Weights: " + professionalWeights);
//        System.out.println("Retail Item Weights: " + retailItemWeights);
//
//        initializer.secondStageValues("construction", Arrays.asList("commercial buildings", "sky scrapers"));
//
//        // Print the initialized maps
//        System.out.println("Professional Weights: " + professionalWeights);
//        System.out.println("Retail Item Weights: " + retailItemWeights);
//
//        // Filter and sort the professionalWeights map
//        List<Map.Entry<String, Integer>> filteredProfessionalWeights = new ArrayList<>(professionalWeights.entrySet());
//        filteredProfessionalWeights.removeIf(entry -> entry.getValue() <= 0);
//        filteredProfessionalWeights.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
//
//        // Filter and sort the retailItemWeights map
//        List<Map.Entry<String, Integer>> filteredRetailItemWeights = new ArrayList<>(retailItemWeights.entrySet());
//        filteredRetailItemWeights.removeIf(entry -> entry.getValue() <= 0);
//        filteredRetailItemWeights.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
//
//        System.out.println("Filtered and Sorted Professional Weights: " + filteredProfessionalWeights);
//        System.out.println("Filtered and Sorted Retail Item Weights: " + filteredRetailItemWeights);
//
//        List<String> filteredProfessionalTags = new ArrayList<>();
//        for (Map.Entry<String, Integer> entry : filteredProfessionalWeights) {
//            filteredProfessionalTags.add(entry.getKey());
//        }
//
//        List<String> filteredRetailItemTags = new ArrayList<>();
//        for (Map.Entry<String, Integer> entry : filteredRetailItemWeights) {
//            filteredRetailItemTags.add(entry.getKey());
//        }
//
//        System.out.println("Filtered and Sorted Professional Tags: " + filteredProfessionalTags);
//        System.out.println("Filtered and Sorted Retail Item Tags: " + filteredRetailItemTags);
//
//        //todo: get the professionals and retail stores based on the filteredprofrssionaltags and filteredretailitemtags
//        //todo: assign them into two different variables and use those variables in the below method to get resultedProfessionals and resultedRetailstores
//
//        List<String> tempvar1 = new ArrayList<>();
//        List<String> tempvar2 = new ArrayList<>();
//
//        initializer.initializeGraph();
//
//        //todo: get the user selected district and assign it to userSelectedDistrict variable
//        var userSelectedDistrict = "ampara";
//
//        List<String> adjacentDistrictsBasedOnUserSelectedDistrict = initializer.getAdjacentDistricts(userSelectedDistrict);
//        System.out.println(adjacentDistrictsBasedOnUserSelectedDistrict);
//
//        List<String> resultedProfessionals = getProfessionalsOrRetailStores(userSelectedDistrict, adjacentDistrictsBasedOnUserSelectedDistrict, "Professional", tempvar1);
//        List<String> resultedRetailstores = getProfessionalsOrRetailStores(userSelectedDistrict, adjacentDistrictsBasedOnUserSelectedDistrict, "retailstore", tempvar2);
//
//        // Sort professionals based on rating (highest to lowest)
//        // resultedRetailstores.sort(Comparator.comparingDouble(product -> product.price));
//
//        // Sort professionals based on rating (highest to lowest)
//        // resultedProfessionals.sort((prof1, prof2) -> Double.compare(prof2.rating, prof1.rating));
//
//    }

}
