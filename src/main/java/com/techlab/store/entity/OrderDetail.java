package com.techlab.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // Clave foránea al Pedido
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id") // Clave foránea al Producto

    private Product product;
    private int cantidad;

    public Product getProduct() {
        return this.product;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}