package com.dardan.movement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMovement;

    @Column(nullable = false)
    private LocalDate movementDate;

    @Column(nullable = false)
    private String movementType;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(name = "id_account")
    private Integer idAccount;

    @ManyToOne
    @JoinColumn(name = "id_account",  referencedColumnName = "id_account",insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_ACCOUNT"))
    private Account account;

    @Column(nullable = false)
    private boolean enabled = true;
    public void changeEnabled() {
        this.enabled = !this.enabled;
    }

}
