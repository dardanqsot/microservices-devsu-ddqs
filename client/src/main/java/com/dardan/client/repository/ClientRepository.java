package com.dardan.client.repository;

import com.dardan.client.model.Client;

import java.util.Optional;

public interface ClientRepository extends GenericRepo<Client, Integer> {

    Optional<Client> findClientByIdClientAndEnabled(Integer id, Boolean enabled);

}
