package org.alexkolo.rest.repository;

import org.alexkolo.rest.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseClientRepository extends JpaRepository<Client, Long> {


}
