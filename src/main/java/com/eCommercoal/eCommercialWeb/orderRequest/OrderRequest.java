package com.eCommercoal.eCommercialWeb.orderRequest;

import java.math.BigDecimal;
import java.util.List;

public class OrderRequest {
    private int id;
    private String orderDescription;
    private List<CartItemRequest> cartItems;
    private BigDecimal totalAmount;

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

    public List<CartItemRequest> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemRequest> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderRequest(int id, String orderDescription, List<CartItemRequest> cartItems, BigDecimal totalAmount) {
        this.id = id;
        this.orderDescription = orderDescription;
        this.cartItems = cartItems;
        this.totalAmount = totalAmount;
    }
    public OrderRequest(){};
}
