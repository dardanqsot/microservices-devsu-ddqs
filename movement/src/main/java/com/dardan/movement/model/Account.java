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

    @Column(nullable = false)
    private Integer idClient;

    @Column(nullable = false, unique=true)
    private String accountNumber;

    @Column(name = "id_account_type", nullable = false)
    private Integer idAccountType;

    @ManyToOne
    @JoinColumn(name = "id_account_type",  referencedColumnName = "id_account_type",insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_ACCOUNT_TYPE"))
    private AccountType accountType;

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
