package org.alexkolo.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexkolo.rest.exception.EntityNotFoundException;
import org.alexkolo.rest.model.Client;
import org.alexkolo.rest.model.Order;
import org.alexkolo.rest.repository.InMemoryOrderRepository;
import org.alexkolo.rest.service.ClientService;
import org.alexkolo.rest.service.OrderService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InMemoryOrderServiceImpl implements OrderService {

    private final InMemoryOrderRepository orderRepository;

    private final ClientService inMemoryClientServiceImpl;


    @Override
    public List<Order> findAll() {
        log.debug("Call findAll in InMemoryOrderServiceImpl");

        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        log.debug("Call findById in InMemoryOrderServiceImpl " + id);

        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Order not found! ID: {0}", id)));
    }

    @Override
    public Order save(Order order) {
        log.debug("Call save in InMemoryOrderServiceImpl " + order);

        Long clientId = order.getClient().getId();
        Client client = inMemoryClientServiceImpl.findById(clientId);
        order = orderRepository.save(order);//опасность цикличности
        client.addOrder(order);                //опасность цикличности
        order.setClient(client);               //опасность цикличности
        return order;
    }

    @Override
    public Order update(Order newOrder) {
        log.debug("Call update in InMemoryOrderServiceImpl " + newOrder);

        Long id = newOrder.getId();
        Order existedOrder = findById(id);
        checkForUpdate(existedOrder);

        //BeanUtils.copyNonNullProperties(order, existedOrder);//тут все делаю
        long newOrderClientId = newOrder.getClient().getId();
        long existedOrderClientId = existedOrder.getClient().getId();
        if (newOrderClientId != existedOrderClientId) {
            Client newClient = inMemoryClientServiceImpl.findById(newOrder.getClient().getId());
            Client existedClient = inMemoryClientServiceImpl.findById(newOrder.getClient().getId());
            existedClient.removeOrder(id);
            newClient.addOrder(existedOrder);
            existedOrder.setClient(newClient);
        }
        existedOrder.setProduct(newOrder.getProduct());
        existedOrder.setCost(newOrder.getCost());
        existedOrder.setUpdateAt(Instant.now());

        return orderRepository.update(existedOrder);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call deleteById in InMemoryOrderServiceImpl " + id);

        Order order = findById(id);
        Client client = inMemoryClientServiceImpl.findById(order.getClient().getId());
        client.removeOrder(id);

        orderRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        log.debug("Call deleteByIdIn in InMemoryOrderServiceImpl " + ids);

        orderRepository.deleteByIdIn(ids);
    }

}
