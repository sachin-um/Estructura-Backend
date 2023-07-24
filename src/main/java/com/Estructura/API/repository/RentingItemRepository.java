package com.Estructura.API.repository;

import com.Estructura.API.model.Renter;
import com.Estructura.API.model.RentingCategory;
import com.Estructura.API.model.RentingItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentingItemRepository extends JpaRepository<RentingItem,Long> {
    List<RentingItem> findAllByRenter(Renter renter);
    List<RentingItem> findRentingItemsByCategory(RentingCategory rentingCategory);
}
