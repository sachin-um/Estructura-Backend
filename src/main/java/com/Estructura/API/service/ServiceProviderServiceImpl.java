package com.Estructura.API.service;

import com.Estructura.API.model.ServiceProvider;
import com.Estructura.API.model.User;
import com.Estructura.API.repository.ServiceProviderRepository;
import com.Estructura.API.repository.UserRepository;
import com.Estructura.API.responses.GenericResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.Estructura.API.model.AccountType.PREMIUM;

@Service
@AllArgsConstructor
public class ServiceProviderServiceImpl implements ServiceProviderService {
    private final ServiceProviderRepository serviceProviderRepository;
    private final UserRepository userRepository;
    @Override
    public Optional<ServiceProvider> findById(Integer id) {
        return serviceProviderRepository.findById(id);
    }

    @Override
    public ResponseEntity<List<ServiceProvider>> getAllServiceProviders() {
        List<ServiceProvider> serviceProviders =
            serviceProviderRepository.findAll();
        if (!serviceProviders.isEmpty()) {
            return ResponseEntity.ok(serviceProviders);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public GenericResponse<Integer> upgradeToPremium(Integer id) {
        GenericResponse<Integer> response     =new GenericResponse<>();
        Optional<User>           optionalUser =userRepository.findById(id);
        if (optionalUser.isPresent()){
            User existedUser=optionalUser.get();
            existedUser.setAccountType(PREMIUM);
            User savedUser=userRepository.save(existedUser);
            if (savedUser.getAccountType()==PREMIUM){
                response.setSuccess(true);
            }else {
                response.setSuccess(false);
                response.setMessage("Something went wrong");
            }
            return response;
        }
        response.setSuccess(false);
        response.setMessage("User doesn't exist");
        return response;
    }
}
