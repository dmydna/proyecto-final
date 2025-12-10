package com.techlab.store.service;

import com.techlab.store.entity.Client;
import com.techlab.store.entity.Order;
import com.techlab.store.entity.OrderDetail;
import com.techlab.store.entity.Product;
import com.techlab.store.repository.ClientRepository;
import com.techlab.store.repository.OrderRepository;
import com.techlab.store.repository.ProductRepository;

//import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
//@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            ClientRepository clientRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(Order order) {

        // TODO
        // falta las validaciones entre varias otras cosas (ej Id existe) .
        // es una version simplicada.

//      if (order.getClient() == null || order.getClient().getId() == null) {
//            throw new RuntimeException("El pedido debe tener un Cliente asignado.");
//      }
        order.setState(Order.orderState.PROCESANDO);
        for (OrderDetail detail : order.getDetails()) {
            detail.setOrder(order);
        }
        return orderRepository.save(order);
    }


    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    @Transactional
    public Order updateOrderStatus(Long id, Order.orderState newState) {
        Order order = this.getOrderById(id);
        log.info("Actualizando estado de Pedido ID {} de {} a {}", id, order.getState(), newState);
        order.setState(newState);
        if (newState == Order.orderState.CANCELADO) {
            log.warn("Pedido cancelado: Reponiendo stock.");
        }
        return orderRepository.save(order);
    }

    public Order updateOrderDetail(Long id, Set<OrderDetail> details){
        Order order = this.getOrderById(id);
        order.setDetails(details);

        return this.orderRepository.save(order);
    }

    public Order editOrderById(Long id, Order dataToEdit) {
        Order order = this.getOrderById(id);
        order.setState(dataToEdit.getState());
        order.setClient(dataToEdit.getClient());
        order.setDetails(dataToEdit.getDetails());
        return this.orderRepository.save(order);
    }
}
