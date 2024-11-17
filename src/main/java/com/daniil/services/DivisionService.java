package com.daniil.services;

import com.daniil.entities.Division;
import com.daniil.repositories.DivisionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DivisionService {

    private DivisionRepository divisionRepository;

    public DivisionService(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    public List<Division> findAllDivisions() {
        return divisionRepository.findAll();
    }

    public Optional<Division> findDivisionById(long id) {
        return divisionRepository.findById(id);
    }
}
