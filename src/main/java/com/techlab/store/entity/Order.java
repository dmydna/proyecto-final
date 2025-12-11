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
    @Getter
    private Long id;

    // enum estado
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private orderState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @Getter @Setter
    private Client client;
  
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter
    private Set<OrderDetail> details = new HashSet<>();


}
