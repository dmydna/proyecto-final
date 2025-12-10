package com.techlab.store.dto;

import com.techlab.store.entity.OrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private Long clientId;
    private List<OrderDetail> details;

    public Long getClientId() {
        return  this.clientId;
    }

    public List<OrderDetail> getDetails() {
        return this.details;
    }
}