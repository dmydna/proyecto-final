package com.techlab.store.dto;


import com.techlab.store.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    Long id;
    Order.OrderState state;
    Set<OrderDetailDTO> details;
}
