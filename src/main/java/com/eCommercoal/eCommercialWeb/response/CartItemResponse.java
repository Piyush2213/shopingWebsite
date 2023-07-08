package com.eCommercoal.eCommercialWeb.response;

public class CartItemResponse {
    private int id;
    private String productName;
    private int quantity;
    private float amount;

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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public CartItemResponse(int id, String productName, int quantity, float amount) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
    }
    public CartItemResponse(){};
}
