package com.dardan.movement.service;

import com.dardan.movement.dto.ReportDto;
import com.dardan.movement.model.Movement;

import java.time.LocalDate;
import java.util.List;

public interface MovementService extends CRUD<Movement, Integer> {
    Movement findMovementByIdMovementAndEnabled(Integer id);

    Movement createMovement(Movement movement);

    Movement updateMovement(Movement movement);

    void deleteMovement(Integer idMovement);

    List<ReportDto> findAllByIdClient(Integer idClient, LocalDate fromDate, LocalDate toDate);
}
