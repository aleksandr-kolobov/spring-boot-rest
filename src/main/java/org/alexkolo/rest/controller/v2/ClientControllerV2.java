package org.alexkolo.rest.controller.v2;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexkolo.rest.mapper.ClientMapper;
import org.alexkolo.rest.model.Client;
import org.alexkolo.rest.model.dto.ClientListResponse;
import org.alexkolo.rest.model.dto.ClientResponse;
import org.alexkolo.rest.model.dto.UpsertClientRequest;
import org.alexkolo.rest.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/clients")
@Tag(name = "Clients V2", description = "Client API version V2")
public class ClientControllerV2 {

    private final ClientService databaseClientServiceImpl;

    private final ClientMapper clientMapper;

    @GetMapping
    public ResponseEntity<ClientListResponse> findAll() {
        return ResponseEntity.ok(
                clientMapper.clientListToResponseList(databaseClientServiceImpl.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                clientMapper.clientToResponse(databaseClientServiceImpl.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid UpsertClientRequest request) {
        Client newClient = databaseClientServiceImpl.save(clientMapper.requestToClient(request));
        return ResponseEntity.ok(
                clientMapper.clientToResponse(newClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long clientId,
                                                 @RequestBody @Valid UpsertClientRequest request) {
        Client updatedClient = databaseClientServiceImpl
                .update(clientMapper.requestToClient(clientId, request));

        return ResponseEntity.ok(
                clientMapper.clientToResponse(updatedClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        databaseClientServiceImpl.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }












}
