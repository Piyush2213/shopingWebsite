package com.eCommercoal.eCommercialWeb.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private int id;

    private BigDecimal totalAmount;
    private int customerId;

    private List<OrderItemResponse> orderItems;
    private LocalDateTime dateTime;
    private List<Long> productIds;

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

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

    public OrderResponse(int id,  BigDecimal totalAmount, int customerId, List<OrderItemResponse> orderItems, LocalDateTime dateTime, List<Long> productIds) {
        this.id = id;

        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.dateTime = dateTime;
        this.productIds = productIds;


    }

    public OrderResponse(){};
}
