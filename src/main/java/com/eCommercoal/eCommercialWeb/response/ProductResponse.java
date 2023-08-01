package com.eCommercoal.eCommercialWeb.response;

import java.math.BigDecimal;

public class ProductResponse  {
    private int id;
    private String name;
    private String imageURL;
    private String subCategory;
    private String category;
    private String colour;
    private String gender;
    private String pUsage;

    private String description;

    private BigDecimal price;


    private int quantity;

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getpUsage() {
        return pUsage;
    }

    public void setpUsage(String pUsage) {
        this.pUsage = pUsage;
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

    public ProductResponse(int id,String name, String description, BigDecimal price, int quantity, String imageURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public ProductResponse(String subCategory, String category, String colour, String gender, String pUsage) {
        this.subCategory = subCategory;
        this.category = category;
        this.colour = colour;
        this.gender = gender;
        this.pUsage = pUsage;
    }

    public ProductResponse(){};
}
