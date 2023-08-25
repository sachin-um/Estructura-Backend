package com.Estructura.API.service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.Painter;
import com.Estructura.API.repository.PainterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PainterServiceImpl implements PainterService {
    private final PainterRepository painterRepository;

    @Override
    public Painter savePainter(Painter painter) {
        Optional<Painter> theArchitect = painterRepository.findByEmail(
            painter.getEmail());
        if (theArchitect.isPresent()) {
            throw new UserAlreadyExistsException(
                "A user with" + painter.getEmail() + "already exists");
        }
        return painterRepository.save(painter);
    }
}

