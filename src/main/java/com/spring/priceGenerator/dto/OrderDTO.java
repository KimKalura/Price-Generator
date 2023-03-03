package com.spring.priceGenerator.dto;

import java.util.List;

public class OrderDTO {
    private List<OrderItemDTO> orderItemDTOList;
    //private Double totalPrice

    public OrderDTO(){}

    public OrderDTO(List<OrderItemDTO> orderItemDTOList) {
        this.orderItemDTOList = orderItemDTOList;
    }

    public List<OrderItemDTO> getOrderItemDTOList() {
        return orderItemDTOList;
    }

    public void setOrderItemDTOList(List<OrderItemDTO> orderItemDTOList) {
        this.orderItemDTOList = orderItemDTOList;
    }
}
