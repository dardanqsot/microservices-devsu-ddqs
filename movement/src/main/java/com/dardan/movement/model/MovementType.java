package com.dardan.movement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MovementType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id_movement_type")
    private Integer idMovementType;

    @Column(nullable = false)
    private String movementType;

}
