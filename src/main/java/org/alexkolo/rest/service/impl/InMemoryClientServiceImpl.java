package org.alexkolo.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexkolo.rest.exception.EntityNotFoundException;
import org.alexkolo.rest.model.Client;
import org.alexkolo.rest.model.Order;
import org.alexkolo.rest.repository.InMemoryClientRepository;
import org.alexkolo.rest.repository.InMemoryOrderRepository;
import org.alexkolo.rest.service.ClientService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InMemoryClientServiceImpl implements ClientService {

    private final InMemoryClientRepository clientRepository;

    private final InMemoryOrderRepository orderRepository;

    @Override
    public List<Client> findAll() {
        log.debug("Call findAll in InMemoryClientServiceImpl");

        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        log.debug("Call findById in InMemoryClientServiceImpl " + id);

        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Client not found! ID: {0}", id)));
    }

    @Override
    public Client save(Client client) {
        log.debug("Call save in InMemoryClientServiceImpl " + client);

        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        log.debug("Call update in InMemoryClientServiceImpl " + client);

        Long id = client.getId();
        Client existedClient = findById(id);
        //BeanUtils.copyNonNullProperties(client, existedClient);
        existedClient.setName(client.getName());//вместо закоментированой строки

        return clientRepository.update(existedClient);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call deleteById in InMemoryClientServiceImpl " + id);

        Client client = findById(id);

        orderRepository.deleteByIdIn(client.getOrders().stream().map(Order::getId).toList());

        clientRepository.deleteById(id);
    }

}
