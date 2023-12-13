package org.alexkolo.rest.service;

import org.alexkolo.rest.exception.UpdateStateException;
import org.alexkolo.rest.model.Order;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findById(Long id);

    Order save(Order order);

    Order update(Order order);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);

    default void checkForUpdate(Order order) {
        if (Duration.between(order.getUpdateAt(), Instant.now()).getSeconds() > 120) {
            throw new UpdateStateException("Too late to update Order");
        }
    }

}
