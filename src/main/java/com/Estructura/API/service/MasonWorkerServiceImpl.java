package com.Estructura.API.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.MasonWorker;
import com.Estructura.API.repository.MasonWorkerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MasonWorkerServiceImpl implements MasonWorkerService {
    private final MasonWorkerRepository masonWorkerRepository;

    @Override
    public MasonWorker saveMasonWorker(MasonWorker masonWorker) {
        Optional<MasonWorker> theArchitect = masonWorkerRepository.findByEmail(masonWorker.getEmail());
        if (theArchitect.isPresent()) {
            throw new UserAlreadyExistsException("A user with" + masonWorker.getEmail() + "already exists");
        }
        return masonWorkerRepository.save(masonWorker);
    }
}
