package com.dardan.movement.repository;

import com.dardan.movement.model.Movement;

import java.util.Optional;


public interface MovementRepository extends GenericRepo<Movement, Integer>{

    Optional<Movement> findMovementByIdMovementAndEnabled(Integer id, Boolean enabled);
}
