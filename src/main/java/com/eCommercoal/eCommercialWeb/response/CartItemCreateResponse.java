package com.eCommercoal.eCommercialWeb.response;

import java.math.BigDecimal;

public class CartItemCreateResponse {
    private int id;
    private String productName;
    private int quantity;
    private BigDecimal amount;
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public CartItemCreateResponse(int id, String productName, int quantity, BigDecimal amount, int productId) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
        this.productId = productId;
    }


    public CartItemCreateResponse(){};
}
