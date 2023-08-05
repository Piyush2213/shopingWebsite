package com.eCommercoal.eCommercialWeb.response;

import java.math.BigDecimal;

public class OrderItemResponse {
    private int productId;
    private int quantity;
    private BigDecimal amount;

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

    public OrderItemResponse(int productId, int quantity, BigDecimal amount) {
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
    }
    public OrderItemResponse(){};
}
