package org.alexkolo.rest.mapper;

import lombok.RequiredArgsConstructor;
import org.alexkolo.rest.model.Client;
import org.alexkolo.rest.model.Order;
import org.alexkolo.rest.service.ClientService;
import org.alexkolo.rest.model.dto.OrderListResponse;
import org.alexkolo.rest.model.dto.OrderResponse;
import org.alexkolo.rest.model.dto.UpsertOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public Order requestToOrder(UpsertOrderRequest request) {
        Order order = new Order();
        order.setProduct(request.getProduct());
        order.setCost(request.getCost());
        Client client = new Client();//временная заглушка
        client.setId(request.getClientId());
        order.setClient(client);

        return order;
    }

    public Order requestToOrder(Long orderId, UpsertOrderRequest request) {
        Order order = requestToOrder(request);
        order.setId(orderId);

        return order;
    }

    public OrderResponse orderToResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setId(order.getId());
        orderResponse.setCost(order.getCost());
        orderResponse.setProduct(order.getProduct());
        orderResponse.setClientId(order.getClient().getId());

        return orderResponse;
    }

    public List<OrderResponse> orderListToResponseList(List<Order> orders) {
        return orders.stream().map(this::orderToResponse).toList();
    }

    public OrderListResponse orderListToOrderListResponse(List<Order> orders) {
        OrderListResponse response = new OrderListResponse();
        response.setOrders(orderListToResponseList(orders));

        return response;
    }

}
