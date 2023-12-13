package org.alexkolo.rest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListResponse {

    private List<OrderResponse> orders = new ArrayList<>();

}
