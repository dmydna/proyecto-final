package com.techlab.store.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
public class Order {

    public enum orderState {
        PROCESANDO,
        COMPLETO,
        CANCELADO,
        EN_ENVIO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // enum estado
    @Enumerated(EnumType.STRING)
    private orderState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
  
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetail> details = new HashSet<>();


    public Client getClient() {
        return this.client;
    }

    public orderState getState() {
        return this.state;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setState(orderState state) {
        this.state = state;
    }

    public void setDetails(Set<OrderDetail> orderDetails) {
        this.details = orderDetails;
    }

    public Set<OrderDetail> getDetails() {
        return this.details;
    }
}
