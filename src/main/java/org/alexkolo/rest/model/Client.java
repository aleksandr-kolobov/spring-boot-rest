package org.alexkolo.rest.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.EAGER)
    private List<Order> orders;

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Long orderId) {
        orders = orders.stream().filter(o -> !o.getId().equals(orderId)).toList();
    }

}
