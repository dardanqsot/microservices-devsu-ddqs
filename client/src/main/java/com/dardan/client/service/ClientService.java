package com.dardan.client.service;

import com.dardan.client.model.Client;

public interface ClientService extends CRUD<Client, Integer> {
    Client findByIdClientAndEnabled(Integer id);

}
