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


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"}, exposedHeaders = { "*" }, methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    private BigDecimal getRandomPrice() {
        Random random = new Random();
        int minPrice = 100;
        int maxPrice = 200000;
        int randomPrice = random.nextInt(maxPrice - minPrice + 1) + minPrice;
        return BigDecimal.valueOf(randomPrice);
    }

    private int getRandomQuantity() {
        Random random = new Random();
        int minQuantity = 1;
        int maxQuantity = 1000;
        return random.nextInt(maxQuantity - minQuantity + 1) + minQuantity;
    }

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
            response.setImageURL(product.getImageURL());
            responseList.add(response);
        }

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id) {
/*
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/piyush/Desktop/fashion.csv"))) {
            //ProductId,Gender,Category,SubCategory,ProductType,Colour,Usage,ProductTitle,Image,ImageURL
            //price, description, quantity
            String line;
            List<Product> products = new ArrayList();
            br.readLine();
            while ((line = br.readLine()) != null) {
                List<String> values = Arrays.asList(line.split(","));
                Product product = new Product(
                        Integer.parseInt(values.get(0)), //productId
                        values.get(1),//Gender
                        values.get(2),//category
                        values.get(3),//subCategory
                        values.get(4),//productType
                        values.get(5),//colour
                        values.get(6),//usage
                        values.get(7),//productTitle
                        values.get(9)//imageURL
                );
                product.setPrice(getRandomPrice());
                product.setQuantity(getRandomQuantity());

                products.add(product);
            }
            productRepository.saveAll(products);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/
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
        response.setImageURL(product.getImageURL());
        response.setColour(product.getColour());
        response.setSubCategory(product.getSubCategory());
        response.setCategory(product.getCategory());
        response.setGender(product.getGender());
        response.setpUsage(product.getpUsage());



        return ResponseEntity.ok(response);
    }




    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {

        Product newProduct = new Product();
        newProduct.setName(productRequest.getName());
        newProduct.setDescription(productRequest.getDescription());
        newProduct.setPrice(productRequest.getPrice());
        newProduct.setQuantity(productRequest.getQuantity());
        newProduct.setImageURL(productRequest.getImageURL());


        Product savedProduct = productRepository.save(newProduct);

        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setQuantity(savedProduct.getQuantity());
        response.setImageURL(savedProduct.getImageURL());


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
        existingProduct.setImageURL(productRequest.getImageURL());




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
