package org.alexkolo.rest.controller.v2;


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
@RequestMapping("/api/v2/orders")
@Tag(name = "Orders V2", description = "Orders API version V2")
public class OrderControllerV2 {

    private final OrderService databaseOrderServiceImpl;

    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<OrderListResponse> findAll() {
        return ResponseEntity.ok(
                orderMapper.orderListToOrderListResponse(databaseOrderServiceImpl.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                orderMapper.orderToResponse(databaseOrderServiceImpl.findById(id)));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid UpsertOrderRequest request) {

        Order order = orderMapper.requestToOrder(request);
        return ResponseEntity.ok(
                orderMapper.orderToResponse(databaseOrderServiceImpl.save(order)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable("id") Long orderId,
                                                @RequestBody @Valid UpsertOrderRequest request) {
        Order updatedOrder = databaseOrderServiceImpl
                .update(orderMapper.requestToOrder(orderId, request));

        return ResponseEntity.ok(orderMapper.orderToResponse(updatedOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

        databaseOrderServiceImpl.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
