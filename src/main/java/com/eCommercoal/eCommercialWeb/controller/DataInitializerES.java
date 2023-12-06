package com.eCommercoal.eCommercialWeb.controller;

import com.eCommercoal.eCommercialWeb.entity.ProductES;
import com.eCommercoal.eCommercialWeb.repository.ProductESRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializerES {

    @Autowired
    private ProductESRepository productESRepository;

    private BigDecimal getRandomPrice() {
        Random random = new Random();
        int minPrice = 100;
        int maxPrice = 2000;
        int randomPrice = random.nextInt(maxPrice - minPrice + 1) + minPrice;
        return BigDecimal.valueOf(randomPrice);
    }

    @PostConstruct
    public void initializeData() {
        if (productESRepository.count() == 0) {
            try (BufferedReader br = new BufferedReader(new FileReader("fashion.csv"))) {
                // ProductId,Gender,Category,SubCategory,ProductType,Colour,Usage,ProductTitle,Image,ImageURL
                // price, description, quantity
                String line;
                List<ProductES> products = new ArrayList<>();
                br.readLine();
                while ((line = br.readLine()) != null) {
                    List<String> values = Arrays.asList(line.split(","));
                    ProductES product = new ProductES(
                            Integer.parseInt(values.get(0)),
                            values.get(7), // productTitle
                            getRandomPrice()
                    );
                    products.add(product);
                }
                productESRepository.saveAll(products);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
