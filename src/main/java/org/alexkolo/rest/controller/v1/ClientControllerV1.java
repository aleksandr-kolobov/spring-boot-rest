package org.alexkolo.rest.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.alexkolo.rest.mapper.ClientMapper;
import org.alexkolo.rest.model.Client;
import org.alexkolo.rest.model.dto.ClientListResponse;
import org.alexkolo.rest.model.dto.ClientResponse;
import org.alexkolo.rest.model.dto.ErrorResponse;
import org.alexkolo.rest.model.dto.UpsertClientRequest;
import org.alexkolo.rest.service.ClientService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
@Tag(name = "Clients V1", description = "Clients API version V1")
public class ClientControllerV1 {

    private final ClientService inMemoryClientServiceImpl;

    private final ClientMapper clientMapper;

    @Operation(
            summary = "Get clients",
            description = "Get all clients",
            tags = {"client"}
    )
    @GetMapping
    public ResponseEntity<ClientListResponse> findAll() {
        return ResponseEntity.ok(
                clientMapper.clientListToResponseList(inMemoryClientServiceImpl.findAll()));
    }


    @Operation(
            summary = "Get client",
            description = "Get client by Id. Return id, name and list of orders",
            tags = {"client", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = ClientResponse.class),
                                    mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                clientMapper.clientToResponse(inMemoryClientServiceImpl.findById(id)));
    }

    @Operation(
            summary = "Create client",
            description = "Create client by name. Return id , name and empty list of orders",
            tags = {"client"}
    )
    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid UpsertClientRequest request) {
        Client newClient = inMemoryClientServiceImpl.save(clientMapper.requestToClient(request));
        return ResponseEntity.ok(
                clientMapper.clientToResponse(newClient));
    }

    @Operation(
            summary = "Change client",
            description = "Change client name by Id. Return id, name and list or orders",
            tags = {"client", "id"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long clientId,
                                                 @RequestBody @Valid UpsertClientRequest request) {
        Client updatedClient = inMemoryClientServiceImpl
                .update(clientMapper.requestToClient(clientId, request));

        return ResponseEntity.ok(
                clientMapper.clientToResponse(updatedClient));
    }

    @Operation(
            summary = "Delete client",
            description = "Delete client by Id",
            tags = {"client", "id"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        inMemoryClientServiceImpl.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

/*  сработает только для этого контроллера!
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> notFoundHandler(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }*/

}
