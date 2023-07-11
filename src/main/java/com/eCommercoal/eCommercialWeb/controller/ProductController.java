package com.eCommercoal.eCommercialWeb.controller;


import com.eCommercoal.eCommercialWeb.entity.Product;
import com.eCommercoal.eCommercialWeb.exception.CustomError;
import com.eCommercoal.eCommercialWeb.exception.ExistsException;
import com.eCommercoal.eCommercialWeb.request.ProductRequest;
import com.eCommercoal.eCommercialWeb.repository.ProductRepository;
import com.eCommercoal.eCommercialWeb.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ExistsException("Nothing Added Yet.");
        }

        List<ProductResponse> responseList = new ArrayList<>();

        for (Product product : products) {
            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setName(product.getName());
            response.setDescription(product.getDescription());
            response.setPrice(product.getPrice());
            response.setQuantity(product.getQuantity());
            responseList.add(response);
        }

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ExistsException("Product Not Found");
        }

        Product product = optionalProduct.get();
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());

        return ResponseEntity.ok(response);
    }




    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {

        Product newProduct = new Product();
        newProduct.setName(productRequest.getName());
        newProduct.setDescription(productRequest.getDescription());
        newProduct.setPrice(productRequest.getPrice());
        newProduct.setQuantity(productRequest.getQuantity());

        Product savedProduct = productRepository.save(newProduct);

        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setQuantity(savedProduct.getQuantity());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest productRequest) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ExistsException("Product not Found.");
        }

        Product existingProduct = optionalProduct.get();
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setQuantity(productRequest.getQuantity());

        Product updatedProduct = productRepository.save(existingProduct);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomError> deleteProduct(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ExistsException("Product not Found.");
        }
        productRepository.deleteById(id);
        CustomError response = new CustomError("Product deleted successfully");

        return ResponseEntity.ok(response);
    }

}
