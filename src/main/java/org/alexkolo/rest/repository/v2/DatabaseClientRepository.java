package org.alexkolo.rest.repository.v2;

import org.alexkolo.rest.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseClientRepository extends JpaRepository<Client, Long> {


}
