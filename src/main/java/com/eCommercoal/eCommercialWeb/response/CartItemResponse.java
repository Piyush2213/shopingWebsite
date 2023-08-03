package com.eCommercoal.eCommercialWeb.response;

import com.eCommercoal.eCommercialWeb.entity.Product;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CartItemResponse {
    private int id;
    private String productName;
    private String imageURL;

    private int quantity;
    private BigDecimal amount;
    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public CartItemResponse(int id, String productName, int quantity, BigDecimal amount, String imageURL, BigDecimal totalAmount) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
        this.imageURL = imageURL;
        this.totalAmount = totalAmount;
    }
    public CartItemResponse(){};
}
