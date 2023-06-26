package com.eCommercoal.eCommercialWeb.Product;

import com.eCommercoal.eCommercialWeb.Dto.admin;
import com.eCommercoal.eCommercialWeb.Dto.product;
import com.eCommercoal.eCommercialWeb.Repository.adminRepository;
import com.eCommercoal.eCommercialWeb.Repository.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/api")
public class productCURDOperations {
    @Autowired
    private productRepository productRepository;

    @Autowired
    private adminRepository adminRepository;


    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id){
        Optional<product>  optionalProduct = productRepository.findProductById(id);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        product product = optionalProduct.get();
        return ResponseEntity.ok(product);
    }
    @PostMapping("/admin/product/add")
    public ResponseEntity<?> addProduct(@RequestBody productDetails productDetails, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        admin admin = adminRepository.findByToken(token);
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid admin token");
        }
        product new_product = new product();
        new_product.setName(productDetails.getName());
        new_product.setDescription(productDetails.getDescription());
        new_product.setPrice(productDetails.getPrice());
        new_product.setQuantity(productDetails.getQuantity());

        product savedProduct = productRepository.save(new_product);

        return ResponseEntity.ok(savedProduct);


    }

    @PutMapping("/admin/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody productDetails productDetails, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        admin admin = adminRepository.findByToken(token);
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid admin token");
        }

        Optional<product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        product existingProduct = optionalProduct.get();
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setQuantity(productDetails.getQuantity());

        product updatedProduct = productRepository.save(existingProduct);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        admin admin = adminRepository.findByToken(token);
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid admin token");
        }
        Optional<product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.deleteById(id);

        return ResponseEntity.ok("Product deleted successfully");
    }
}
