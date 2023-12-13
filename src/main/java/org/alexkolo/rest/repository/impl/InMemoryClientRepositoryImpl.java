package org.alexkolo.rest.repository.impl;

import org.alexkolo.rest.model.Client;

import lombok.extern.slf4j.Slf4j;

import org.alexkolo.rest.repository.InMemoryClientRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class InMemoryClientRepositoryImpl implements InMemoryClientRepository {

    private final Map<Long, Client> clients = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public List<Client> findAll() {
        log.debug("Call findAll InMemoryClientRepository");

        return new ArrayList<>(clients.values());
    }

    @Override
    public Optional<Client> findById(Long id) {
        log.debug("Call findById InMemoryClientRepository " + id);

        return Optional.ofNullable(clients.get(id));
    }

    @Override
    public Client save(Client client) {
        log.debug("Call save InMemoryClientRepository " + client);

        Long id = currentId.getAndIncrement();
        client.setId(id);

        clients.put(id, client);

        return client;
    }

    @Override
    public Client update(Client client) {
        log.debug("Call update InMemoryClientRepository " + client);

        Long id = client.getId();
        //перенес в сервис
        //Client existedClient = clients.get(id);
        //existedClient.setName(client.getName());

        clients.put(id, client);

        return client;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call deleteById InMemoryClientRepository " + id);

        clients.remove(id);
    }

}
