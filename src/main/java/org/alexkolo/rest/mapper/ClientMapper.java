package org.alexkolo.rest.mapper;

import lombok.RequiredArgsConstructor;
import org.alexkolo.rest.model.Client;
import org.alexkolo.rest.model.dto.ClientListResponse;
import org.alexkolo.rest.model.dto.ClientResponse;
import org.alexkolo.rest.model.dto.UpsertClientRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final OrderMapper orderMapper;

    public Client requestToClient(UpsertClientRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        client.setOrders(new ArrayList<>());

        return client;
    }

    public Client requestToClient(Long clientId, UpsertClientRequest request) {
        Client client = requestToClient(request);
        client.setId(clientId);

        return client;
    }

    public ClientResponse clientToResponse(Client client) {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(client.getId());
        clientResponse.setName(client.getName());
        clientResponse.setOrders(orderMapper.orderListToResponseList(client.getOrders()));

        return clientResponse;
    }

    public ClientListResponse clientListToResponseList(List<Client> clients) {
        ClientListResponse response = new ClientListResponse();
        response.setClients(clients.stream().map(this::clientToResponse).toList());

        return response;
    }

}
