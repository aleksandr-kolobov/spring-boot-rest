package org.alexkolo.rest.repository;

import org.alexkolo.rest.model.Order;

import java.util.List;
import java.util.Optional;

public interface InMemoryOrderRepository {

    List<Order> findAll();

    Optional<Order> findById(Long id);

    Order save(Order order);

    Order update(Order order);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);

}
