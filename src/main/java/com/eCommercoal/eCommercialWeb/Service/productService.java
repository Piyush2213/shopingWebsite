package com.eCommercoal.eCommercialWeb.Service;

import org.springframework.stereotype.Service;

import com.eCommercoal.eCommercialWeb.Entity.product;
import com.eCommercoal.eCommercialWeb.Repository.productRepository;

import java.util.List;

@Service
public class productService {

    private productRepository productRepository;

    public productService(productRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<product> getAllProducts() {
        return this.productRepository.findAll();
    }
}
