package com.dardan.movement.repository;

import com.dardan.movement.model.Movement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovementRepository extends GenericRepo<Movement, Integer> {

    Optional<Movement> findMovementByIdMovementAndEnabled(Integer id, Boolean enabled);

    @Query("SELECT m FROM Movement m WHERE m.account.idClient = :idClient AND m.movementDate between :fromDate AND :toDate AND m.enabled = true")
    List<Movement> findAllByIdClientAndDates(@Param("idClient") Integer idClient, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("SELECT m FROM Movement m WHERE m.account.idClient = :idClient")
    List<Movement> findAllByIdClient(@Param("idClient") Integer idClient);

}