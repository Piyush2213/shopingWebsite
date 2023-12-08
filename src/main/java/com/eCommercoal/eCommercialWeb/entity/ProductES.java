package com.eCommercoal.eCommercialWeb.entity;

import jakarta.persistence.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Document(indexName = "product_index")
public class ProductES {
    @Id
    private int id;
    @Field(type = FieldType.Text, analyzer = "standard")
    private String name;
    @Field(type = FieldType.Keyword)
    private BigDecimal price;


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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductES(int id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
