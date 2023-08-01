package com.eCommercoal.eCommercialWeb.request;


import java.math.BigDecimal;

public class ProductRequest {

    private String name;
    private String imageURL;


    private String description;

    private BigDecimal price;


    private int quantity;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductRequest(String name, String description, BigDecimal price, int quantity, String imageURL) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }
    public ProductRequest(){};
}
