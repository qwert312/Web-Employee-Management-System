package com.daniil.services;

import com.daniil.entities.Position;
import com.daniil.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionService {
    private PositionRepository positionRepository;

    @Autowired
    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> findAllPositions() {
        return positionRepository.findAll();
    }

    public Optional<Position> findPositionById(long id) {
        return positionRepository.findById(id);
    }
}
