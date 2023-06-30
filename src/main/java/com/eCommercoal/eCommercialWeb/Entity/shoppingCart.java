package com.eCommercoal.eCommercialWeb.Entity;

import jakarta.persistence.*;
import com.eCommercoal.eCommercialWeb.Entity.product;
@Entity
@Table(name = "cart")
public class shoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private int productId;
    private String productName;
    private int quantity;
    private float amount;


    public shoppingCart(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public shoppingCart(int id, String productName, float amount) {
        this.id = id;
        this.productName = productName;
        this.amount = amount;
    }

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

    public shoppingCart(){};
}
