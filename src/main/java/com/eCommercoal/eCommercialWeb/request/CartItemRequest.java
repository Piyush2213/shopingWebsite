package com.eCommercoal.eCommercialWeb.request;

import java.math.BigDecimal;

public class CartItemRequest {
    private int id;
    private int productId;
    private int quantity;
    private BigDecimal amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CartItemRequest(int id, int productId, int quantity, BigDecimal amount) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
    }
    public CartItemRequest(){}
}
