package org.alexkolo.rest.repository.v1.impl;

import org.alexkolo.rest.model.Order;

import lombok.extern.slf4j.Slf4j;

import org.alexkolo.rest.repository.v1.InMemoryOrderRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class InMemoryOrderRepositoryImpl implements InMemoryOrderRepository {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public List<Order> findAll() {
        log.debug("Call findAll InMemoryOrderRepository");

        return new ArrayList<>(orders.values());
    }

    @Override
    public Optional<Order> findById(Long id) {
        log.debug("Call findById InMemoryOrderRepository " + id);

        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public Order save(Order order) {
        log.debug("Call save InMemoryTaskRepository " + order);

        Long id = currentId.getAndIncrement();
        order.setId(id);
        Instant now = Instant.now();
        order.setCreateAt(now);
        order.setUpdateAt(now);

        orders.put(id, order);

        return order;
    }

    @Override
    public Order update(Order order) {
        log.debug("Call update InMemoryOrderRepository " + order);

        Long id = order.getId();
        //все делаем в сервисе

        orders.put(id, order);

        return order;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call deleteById InMemoryOrderRepository " + id);

        orders.remove(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        log.debug("Call deleteByIdIn InMemoryOrderRepository " + ids);

        ids.forEach(orders::remove);
    }

}
