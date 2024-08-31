package com.dardan.client.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClient;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean state;

    @Column(nullable = false)
    private boolean enabled = true;
    public void changeEnabled() {
        this.enabled = !this.enabled;
    }
}
