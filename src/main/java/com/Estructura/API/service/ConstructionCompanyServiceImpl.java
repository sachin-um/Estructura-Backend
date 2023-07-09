package com.Estructura.API.service;

import com.Estructura.API.model.ConstructionCompany;
import com.Estructura.API.repository.ConstructionCompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConstructionCompanyServiceImpl implements ConstructionCompanyService{
    private final ConstructionCompanyRepository constructionCompanyRepository;
    @Override
    public ConstructionCompany saveConstructionCompany(ConstructionCompany constructionCompany) {
        return null;
    }
}
