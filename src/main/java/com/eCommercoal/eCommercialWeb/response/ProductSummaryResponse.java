package com.eCommercoal.eCommercialWeb.response;

import java.math.BigDecimal;

public class ProductSummaryResponse {
    private int id;
    private String name;
    private String imageURL;
    private BigDecimal price;
    private String description;
    private int quantity;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public ProductSummaryResponse(int id, String name, String imageURL, BigDecimal price,  int quantity) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;

        this.quantity = quantity;
    }
    public ProductSummaryResponse() {
    }
}
