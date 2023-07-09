package com.eCommercoal.eCommercialWeb.response;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CartItemResponse {
    private int id;
    private String productName;
    private int quantity;
    private BigDecimal amount;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getFormattedAmount() {
        return DECIMAL_FORMAT.format(amount);
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

    public CartItemResponse(int id, String productName, int quantity, BigDecimal amount) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
    }
    public CartItemResponse(){};
}
