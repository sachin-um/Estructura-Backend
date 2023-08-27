package com.Estructura.API.service;

import com.Estructura.API.repository.CartRepository;
import com.Estructura.API.repository.OrderEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class CartServiceImpl implements CartService {


    private final CartRepository cartRepository;
    private final RetailItemService retailItemService;
    private final OrderEntityRepository orderEntityRepository;

//    @Override
//    @Transactional
//    public String addToCart(OrderRequestBody orderRequestBody){
//        Authentication auth = SecurityContextHolder.getContext()
//        .getAuthentication();
//        //UserInfo userInfo = (UserInfo) auth.getPrincipal();
//        //Optional<ShoppingCart> shoppingCartCheck = cartRepository
//        .findByUserInfo(userInfo);
//
//        ShoppingCart shoppingCart;
////        if(!shoppingCartCheck.isPresent()){
////            shoppingCart = new ShoppingCart(userInfo);
////            cartRepository.save(shoppingCart);
////        }else{
////            shoppingCart = shoppingCartCheck.get();
////        }
//
//        Optional<RetailItem> retailItem = retailItemService
//        .findRetailItemById(orderRequestBody.getId());
//        System.out.println(retailItem.toString());
//
//        if(!retailItem.isPresent()){
//            throw new IllegalStateException("retail item id error/not found");
//        }
//
//        OrderEntity orderEntity = new OrderEntity(orderRequestBody.getNum()
//        , retailItem.get());
//        orderEntityRepository.save(orderEntity);
////        shoppingCart.getRetailItems().add(orderEntity);
//
//        return "Done";
//    }
//
//    @Override
//    @Transactional
//    public String addToCartMultiple(List<OrderRequestBody> orders){
//        System.out.println("orders: " + orders.toString());
//        for(OrderRequestBody body: orders){
//            System.out.println("body: " + body.toString());
//            this.addToCart(body);
//        }
//
//        return "Done";
//    }
//
//
//    //past orders?? check on shopping carts
//    @Override
//    public List<OrderEntity> findOrderedRetailItems(){
//
//        Authentication auth = SecurityContextHolder.getContext()
//        .getAuthentication();
//        //UserInfo userInfo = (UserInfo) auth.getPrincipal();
//        //List<ShoppingCart> shoppingCarts = cartRepository
//        .findAllByUserInfo(userInfo);
//        List<OrderEntity> retailItems = new ArrayList<OrderEntity>();
//
////        for(int i = shoppingCarts.size()-1; i>=0; i--){
////            retailItems.addAll(shoppingCarts.get(i).getRetailItems());
////        }
//
//        return retailItems;
//    }


}
