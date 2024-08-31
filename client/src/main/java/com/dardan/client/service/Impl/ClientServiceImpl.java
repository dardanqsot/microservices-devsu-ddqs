package com.dardan.client.service.Impl;

import com.dardan.client.exception.NotFoundException;
import com.dardan.client.model.Client;
import com.dardan.client.repository.ClientRepository;
import com.dardan.client.repository.GenericRepo;
import com.dardan.client.service.ClientService;
import com.dardan.client.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends CRUDImpl<Client, Integer> implements ClientService {

    private final ClientRepository repo;
    @Override
    protected GenericRepo<Client, Integer> getRepo() {
        return repo;
    }

    @Override
    public Client findByIdClientAndEnabled(Integer id) {
        Client client = repo.findClientByIdClientAndEnabled(id, Constants.ENABLED)
                .orElseThrow(NotFoundException.supplier(Constants.NOT_FOUND + id));
        return client;
    }
}