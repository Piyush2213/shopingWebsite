package com.eCommercoal.eCommercialWeb.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eCommercoal.eCommercialWeb.entity.Product;
import com.eCommercoal.eCommercialWeb.repository.ProductRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {
    @Autowired
    private ProductRepository productRepository;

    private BigDecimal getRandomPrice() {
        Random random = new Random();
        int minPrice = 100;
        int maxPrice = 2000;
        int randomPrice = random.nextInt(maxPrice - minPrice + 1) + minPrice;
        return BigDecimal.valueOf(randomPrice);
    }

    private int getRandomQuantity() {
        Random random = new Random();
        int minQuantity = 1;
        int maxQuantity = 1000;
        return random.nextInt(maxQuantity - minQuantity + 1) + minQuantity;
    }

    @PostConstruct
    public void initializeData() {
        if (productRepository.count() == 0) {
            try (BufferedReader br = new BufferedReader(new FileReader("fashion.csv"))) {
                // ProductId,Gender,Category,SubCategory,ProductType,Colour,Usage,ProductTitle,Image,ImageURL
                // price, description, quantity
                String line;
                List<Product> products = new ArrayList();
                br.readLine();
                while ((line = br.readLine()) != null) {
                    List<String> values = Arrays.asList(line.split(","));
                    Product product = new Product(
                            Integer.parseInt(values.get(0)), // productId
                            values.get(1), // Gender
                            values.get(2), // category
                            values.get(3), // subCategory
                            values.get(4), // productType
                            values.get(5), // colour
                            values.get(6), // usage
                            values.get(7), // productTitle
                            values.get(9)// imageURL
                    );
                    product.setPrice(getRandomPrice());
                    product.setQuantity(getRandomQuantity());

                    products.add(product);
                }
                productRepository.saveAll(products);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
