package org.alexkolo.rest.repository;

import org.alexkolo.rest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseOrderRepository extends JpaRepository<Order, Long> {


}
