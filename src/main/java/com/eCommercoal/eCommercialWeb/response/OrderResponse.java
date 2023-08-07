package com.eCommercoal.eCommercialWeb.response;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponse {
    private int id;

    private BigDecimal totalAmount;
    private int customerId;

    private List<OrderItemResponse> orderItems;




    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemResponse> orderItems) {
        this.orderItems = orderItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public OrderResponse(int id,  BigDecimal totalAmount, int customerId, List<OrderItemResponse> orderItems) {
        this.id = id;

        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.orderItems = orderItems;


    }

    public OrderResponse(){};
}
