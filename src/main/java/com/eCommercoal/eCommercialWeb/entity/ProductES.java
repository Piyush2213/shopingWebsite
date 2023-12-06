package com.eCommercoal.eCommercialWeb.entity;

import jakarta.persistence.*;
import org.springframework.data.elasticsearch.annotations.Document;
import java.math.BigDecimal;

@Document(indexName = "product_index")
public class ProductES {
    @Id
    private int id;
    private String name;
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
