package com.eCommercoal.eCommercialWeb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "product_index")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductES {
    @Id
    private int id;
    private String name;
    private BigDecimal price;


}
