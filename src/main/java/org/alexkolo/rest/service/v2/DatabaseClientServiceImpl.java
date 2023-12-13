package org.alexkolo.rest.service.v2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexkolo.rest.exception.EntityNotFoundException;
import org.alexkolo.rest.model.Client;
import org.alexkolo.rest.repository.v2.DatabaseClientRepository;
import org.alexkolo.rest.service.ClientService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseClientServiceImpl implements ClientService {

    private final DatabaseClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        log.debug("Call findAll in DatabaseClientServiceImpl");

        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        log.debug("Call findById in DatabaseClientServiceImpl");

        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Client not found! ID: {0}", id)));
    }

    @Override
    public Client save(Client client) {
        log.debug("Call save in DatabaseClientServiceImpl");

        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        log.debug("Call update in DatabaseClientServiceImpl");

        Long id = client.getId();
        Client existedClient = findById(id);
        //BeanUtils.copyNonNullProperties(client, existedClient);
        existedClient.setName(client.getName());//вместо закоментированой строки

        return clientRepository.save(client);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call deleteById in DatabaseClientServiceImpl");

        clientRepository.deleteById(id);
    }

}
