package com.eCommercoal.eCommercialWeb.response;

import java.math.BigDecimal;

public class OrderResponse {
    private int id;
    private String orderDescription;
    private BigDecimal totalAmount;
    private int customerId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
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

    public OrderResponse(int id, String orderDescription, BigDecimal totalAmount, int customerId) {
        this.id = id;
        this.orderDescription = orderDescription;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
    }

    public OrderResponse(){};
}
