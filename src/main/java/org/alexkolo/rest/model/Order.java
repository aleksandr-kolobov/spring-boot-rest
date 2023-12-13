package org.alexkolo.rest.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    private BigDecimal cost;

    @ToString.Exclude
    //@JoinColumn(name = "client_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @CreationTimestamp
    private Instant createAt;

    @UpdateTimestamp
    private Instant updateAt;

}
