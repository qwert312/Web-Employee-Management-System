package com.daniil.repositories;

import com.daniil.entities.Division;
import com.daniil.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
}
