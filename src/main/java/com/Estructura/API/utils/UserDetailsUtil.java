package com.Estructura.API.utils;

import com.Estructura.API.model.*;
import com.Estructura.API.repository.QualificationRepository;
import com.Estructura.API.repository.ServiceAreaRepository;
import com.Estructura.API.repository.SpecializationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.Estructura.API.model.Role.ARCHITECT;

@Service
@AllArgsConstructor
public class UserDetailsUtil {
    private final QualificationRepository qualificationRepository;
    private final SpecializationRepository specializationRepository;
    private final ServiceAreaRepository serviceAreaRepository;
    public void saveSpecialization(User user, String specialization) {
        var theSpecialization = Specialization.builder()
                                              .specialization(specialization)
                                              .build();
        if (user.getRole().equals(ARCHITECT)) {
            theSpecialization.setArchitect((Architect) user);
        }
        specializationRepository.save(theSpecialization);
    }

    public void saveQualification(User user, String qualification) {
        var theQualification = Qualification.builder()
                                            .qualification(qualification)
                                            .build();
        if (user.getRole().equals(ARCHITECT)) {
            theQualification.setArchitect((Architect) user);
        }
        qualificationRepository.save(theQualification);
    }

    public void saveServiceArea(User user, String serviceArea) {
        var theServiceArea = ServiceArea.builder().serviceArea(serviceArea)
                                        .build();
        if (user.getRole().equals(ARCHITECT)) {
            theServiceArea.setProfessional((Professional) user);
        }
        serviceAreaRepository.save(theServiceArea);
    }
}
