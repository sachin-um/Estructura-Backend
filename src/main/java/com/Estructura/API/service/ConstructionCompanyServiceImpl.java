package com.Estructura.API.service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.ConstructionCompany;
import com.Estructura.API.repository.ConstructionCompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConstructionCompanyServiceImpl implements
    ConstructionCompanyService {
    private final ConstructionCompanyRepository constructionCompanyRepository;

    @Override
    public ConstructionCompany saveConstructionCompany(
        ConstructionCompany constructionCompany) {
        Optional<ConstructionCompany> theCompany = constructionCompanyRepository
            .findByEmail(constructionCompany.getEmail());
        if (theCompany.isPresent()) {
            throw new UserAlreadyExistsException(
                "A user with" + constructionCompany.getEmail() +
                "already exists");
        }
        return constructionCompanyRepository.save(constructionCompany);
    }
}
