package com.dardan.movement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id_account")
    private Integer idAccount;

    private Integer idClient;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String accountType;

    @Column(nullable = false)
    private BigDecimal initialBalance;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Movement> movements;

    @Column(nullable = false)
    private boolean enabled = true;
    public void changeEnabled() {
        this.enabled = !this.enabled;
    }

}
