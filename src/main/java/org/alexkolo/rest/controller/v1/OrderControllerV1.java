package org.alexkolo.rest.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.alexkolo.rest.mapper.OrderMapper;
import org.alexkolo.rest.model.Client;
import org.alexkolo.rest.model.Order;
import org.alexkolo.rest.model.dto.OrderListResponse;
import org.alexkolo.rest.model.dto.OrderResponse;
import org.alexkolo.rest.model.dto.UpsertOrderRequest;
import org.alexkolo.rest.service.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders V1", description = "Orders API version V1")
public class OrderControllerV1 {

    private final OrderService inMemoryOrderServiceImpl;

    private final OrderMapper orderMapper;

    @Operation(
            summary = "Get orders",
            description = "Get all orders",
            tags = {"order"}
    )
    @GetMapping
    public ResponseEntity<OrderListResponse> findAll() {
        return ResponseEntity.ok(
                orderMapper.orderListToOrderListResponse(inMemoryOrderServiceImpl.findAll()));
    }

    @Operation(
            summary = "Get order",
            description = "Get order by Id. Return product, cost and clientId",
            tags = {"order", "id"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                orderMapper.orderToResponse(inMemoryOrderServiceImpl.findById(id)));
    }

    @Operation(
            summary = "Create order",
            description = "Create order. Return product, cost and clientId",
            tags = {"order"}
    )
    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid UpsertOrderRequest request) {

        Order order = orderMapper.requestToOrder(request);
        return ResponseEntity.ok(
                orderMapper.orderToResponse(inMemoryOrderServiceImpl.save(order)));
    }

    @Operation(
            summary = "Change order",
            description = "Change order by Id. Return product, cost and clientId",
            tags = {"order", "id"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable("id") Long orderId,
                                                 @RequestBody @Valid UpsertOrderRequest request) {
        Order updatedOrder = inMemoryOrderServiceImpl
                .update(orderMapper.requestToOrder(orderId, request));

        return ResponseEntity.ok(orderMapper.orderToResponse(updatedOrder));
    }

    @Operation(
            summary = "Delete order",
            description = "Delete order by Id.",
            tags = {"order", "id"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

        inMemoryOrderServiceImpl.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
