package com.eCommercoal.eCommercialWeb.service;

import com.eCommercoal.eCommercialWeb.entity.Product;
import com.eCommercoal.eCommercialWeb.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void updateProductQuantity(int productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        int updatedQuantity = product.getQuantity() - quantity;
        if (updatedQuantity < 0) {
            throw new IllegalArgumentException("Insufficient product quantity");
        }

        product.setQuantity(updatedQuantity);
        productRepository.save(product);
    }
}
