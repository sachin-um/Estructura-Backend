package com.Estructura.API.repository;

import com.Estructura.API.model.Customer;
import com.Estructura.API.model.ShoppingCart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<ShoppingCart, Long> {
//    Optional<ShoppingCart> findByUserInfo(UserInfo userInfo);

    //    @Query(nativeQuery = true, value = "SELECT * FROM ShoppingCart s
    //    WHERE s.userInfo= :userinfo DESC LIMIT :cnt")
//    List<ShoppingCart> findAllByUserInfo(UserInfo userInfo);

    void deleteAllByCustomer(Customer customer);

    ShoppingCart findByCustomer(Customer customer);


}