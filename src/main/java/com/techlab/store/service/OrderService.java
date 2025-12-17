package com.techlab.store.service;


import com.techlab.store.entity.Order;
import com.techlab.store.entity.OrderDetail;
import com.techlab.store.entity.Product;
import com.techlab.store.repository.ClientRepository;
import com.techlab.store.repository.OrderRepository;
import com.techlab.store.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

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

      if (order.getClient() == null || order.getClient().getId() == null) {
            throw new RuntimeException("El pedido debe tener un Cliente asignado.");
      }
      order.setState(Order.OrderState.PROCESANDO);

      for (OrderDetail detail : order.getDetails()) {
          // establece relacion order<->orderDetail
          detail.setOrder(order);

          Product p = productRepository.findById(detail.getProduct().getId())
                  .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

          if (p.getStock() < detail.getQuantity()) {
              throw new RuntimeException("Stock insuficiente para el producto: " + p.getName());
          }
          // actualiza stock de producto
          int nuevoStock = p.getStock() - detail.getQuantity();
          p.setStock(nuevoStock);
          productRepository.save(p);
      }
      return orderRepository.save(order);
    }


    @Transactional(readOnly = true)
    public Order getOrderById(Long orderId) {
        return orderRepository.findOneWithDetailsAndClientById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + orderId));
    }

   @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAllWithDetailsAndClient();
    }


    @Transactional
    public Order updateOrderStatus(Long id, Order.OrderState newState) {
        Order order = this.getOrderById(id);
        log.info("Actualizando estado de Pedido ID {} de {} a {}", id, order.getState(), newState);
        order.setState(newState);
        if (newState == Order.OrderState.CANCELADO) {
            log.warn("Pedido cancelado: Reponiendo stock.");
        }
        return orderRepository.save(order);
    }

    public Order updateOrderDetail(Long id, Set<OrderDetail> details){
        Order order = this.getOrderById(id);
        order.setDetails(details);

        return this.orderRepository.save(order);
    }


    @Transactional
    public Order editOrderById(Long id, Order dataToEdit) {
        Order existingOrder = this.getOrderById(id);
        validateOrderStateForEdit(existingOrder);

        if (dataToEdit.getClient() != null) {
            existingOrder.setClient(dataToEdit.getClient());
        }

        existingOrder.setState(dataToEdit.getState());
        if (dataToEdit.getDetails() != null) {
            this.updateOrderDetailsAndStock(existingOrder, dataToEdit.getDetails());
        }
        return this.orderRepository.save(existingOrder);
    }





    private void updateOrderDetailsAndStock(Order existingOrder, Set<OrderDetail> newDetails) {

        Map<Long, OrderDetail> oldDetailsMap = existingOrder.getDetails().stream()
                .collect(Collectors.toMap(
                        detail -> detail.getProduct().getId(),
                        detail -> detail
                ));

        for (OrderDetail newDetail : newDetails) {
            Long productId = newDetail.getProduct().getId();
            OrderDetail oldDetail = oldDetailsMap.get(productId);
            // modificamos el stock de los productos
            this.updateStockForModifiedDetail(newDetail, oldDetail);
            newDetail.setOrder(existingOrder);
            // voy vaciando el oldDetail para que solo queden los
            // productos eliminados del pedido
            oldDetailsMap.remove(productId);
        }
        // restaura el stock del los productos eliminados del pedido.
        this.restoreStockForDeletedDetails(oldDetailsMap);
        existingOrder.getDetails().clear();
        existingOrder.getDetails().addAll(newDetails);
    }


    private void validateOrderStateForEdit(Order order) {
        if (order.getState() == Order.OrderState.COMPLETO ||
                order.getState() == Order.OrderState.EN_ENVIO) {
            throw new RuntimeException("No se pueden editar los detalles de una orden en estado COMPLETO o EN_ENVIO.");
        }
    }


    private void updateStockForModifiedDetail(OrderDetail newDetail, OrderDetail oldDetail) {

        Long productId = newDetail.getProduct().getId();
        int newQuantity = newDetail.getQuantity();
        int oldQuantity = (oldDetail != null) ? oldDetail.getQuantity() : 0;

        int stockAdjustment = oldQuantity - newQuantity;

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto con ID " + productId + " no encontrado."));

        if (product.getStock() + stockAdjustment < 0) {
            throw new RuntimeException("Stock insuficiente para el producto: " + product.getName());
        }

        product.setStock(product.getStock() + stockAdjustment);
        productRepository.save(product);
    }


    private void restoreStockForDeletedDetails(Map<Long, OrderDetail> deletedDetailsMap) {
        for (OrderDetail deletedDetail : deletedDetailsMap.values()) {
            Product product = productRepository.findById(deletedDetail.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado."));

            // restaura el stock completo del ítem eliminado
            product.setStock(product.getStock() + deletedDetail.getQuantity());
            productRepository.save(product);
        }
    }

    public List<Order> getOrderByClientId(Long id) {
        return this.orderRepository.findAllWithDetailsAndClientById(id);
    }
}
