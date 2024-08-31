package com.dardan.movement.service;

import com.dardan.movement.model.Movement;

public interface MovementService extends CRUD<Movement, Integer> {
    Movement findMovementByIdMovementAndEnabled(Integer id);

}
