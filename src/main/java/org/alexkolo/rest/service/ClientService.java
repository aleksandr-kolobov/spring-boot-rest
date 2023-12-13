package org.alexkolo.rest.service;

import org.alexkolo.rest.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAll();

    Client findById(Long id);

    Client save(Client client);

    Client update(Client client);

    void deleteById(Long id);

}
