package com.eCommercoal.eCommercialWeb.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "gender")
    private String gender;

    @Column(name = "category")
    private String category;

    @Column(name = "subCategory")
    private String subCategory;

    @Column(name = "productType")
    private String productType;

    @Column(name = "colour")
    private String colour;
    @Column(name = "pUsage")
    private String pUsage;
    @Column(name = "name")
    private String name;

    @Column(name = "imageURL")
    private String imageURL;



    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;




    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getpUsage() {
        return pUsage;
    }

    public void setUsage(String pUsage) {
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

    public Product( String name, String description, BigDecimal price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
    public Product(int id, String gender, String category, String subCategory, String productType,
                   String colour, String pUsage, String name,  String imageURL) {
        this.id = id;
        this.gender = gender;
        this.category = category;
        this.subCategory = subCategory;
        this.productType = productType;
        this.colour = colour;
        this.pUsage = pUsage;
        this.name = name;
        this.imageURL = imageURL;
    }

    public Product(){};
}
