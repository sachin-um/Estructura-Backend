package com.Estructura.API.service;

import com.Estructura.API.model.RetailItem;
import com.Estructura.API.model.RetailItemType;
import lombok.AllArgsConstructor;
import com.Estructura.API.repository.RetailItemRepository;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RetailItemServiceImpl implements RetailItemService {

    private RetailItemRepository retailItemRepository;

    @Override
    public List<RetailItem> fetchAll() {
        return retailItemRepository.findAll();
    }
    @Override
    public Optional<RetailItem> findRetailItemById(Long id){
        return retailItemRepository.findById(id);
    }

    @Override
    public List<RetailItem> fetchAllRetailItems(){
        return retailItemRepository.findAll();
    }

    @Override
    public List<RetailItem> fetchByType(RetailItemType retailItemType){
        return retailItemRepository.findAllByRetailItemType(retailItemType);
    }

    @Override
    public void addRetailItem(){

    }

}