package org.alexkolo.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexkolo.rest.exception.EntityNotFoundException;
import org.alexkolo.rest.model.Client;
import org.alexkolo.rest.model.Order;
import org.alexkolo.rest.repository.DatabaseOrderRepository;
import org.alexkolo.rest.service.OrderService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseOrderServiceImpl implements OrderService {

    private final DatabaseOrderRepository orderRepository;

    private final DatabaseClientServiceImpl clientService;

    @Override
    public List<Order> findAll() {
        log.debug("Call findAll in DatabaseOrderServiceImpl");

        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        log.debug("Call findById in DatabaseOrderServiceImpl");

        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Order not found! ID: {0}", id)));
    }

    @Override
    public Order save(Order order) {
        log.debug("Call save in DatabaseOrderServiceImpl");

        Long clientId = order.getClient().getId();
        Client client = clientService.findById(clientId);
        order.setClient(client);          //опасность цикличности

        return orderRepository.save(order);
    }

    @Override
    public Order update(Order newOrder) {
        log.debug("Call update in DatabaseOrderServiceImpl");

        Long id = newOrder.getId();
        Order existedOrder = findById(id);
        checkForUpdate(existedOrder);

        //BeanUtils.copyNonNullProperties(order, existedOrder);//тут все делаю
        long newOrderClientId = newOrder.getClient().getId();
        long existedOrderClientId = existedOrder.getClient().getId();
        if (newOrderClientId != existedOrderClientId) {
            Client newClient = clientService.findById(newOrder.getClient().getId());
            existedOrder.setClient(newClient);
        }
        existedOrder.setProduct(newOrder.getProduct());
        existedOrder.setCost(newOrder.getCost());
        existedOrder.setUpdateAt(Instant.now());

        return orderRepository.save(existedOrder);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call deleteById in DatabaseOrderServiceImpl");

        orderRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        log.debug("Call deleteByIdIn in DatabaseOrderServiceImpl");

        orderRepository.deleteAllById(ids);
    }
}
