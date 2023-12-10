package com.eCommercoal.eCommercialWeb.controller;


import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.eCommercoal.eCommercialWeb.entity.Product;
import com.eCommercoal.eCommercialWeb.entity.ProductES;
import com.eCommercoal.eCommercialWeb.exception.CustomError;
import com.eCommercoal.eCommercialWeb.exception.CustomResponse;
import com.eCommercoal.eCommercialWeb.exception.ExistsException;
import com.eCommercoal.eCommercialWeb.request.ProductRequest;
import com.eCommercoal.eCommercialWeb.repository.ProductRepository;
import com.eCommercoal.eCommercialWeb.response.ProductResponse;
import com.eCommercoal.eCommercialWeb.response.ProductSummaryResponse;
import com.eCommercoal.eCommercialWeb.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private ElasticSearchService elasticSearchService;


    @GetMapping("/fuzzySearch/{approximateProductName}")
    List<ProductES> fuzzySearch(@PathVariable String approximateProductName) throws IOException{
        SearchResponse<ProductES> searchResponse = elasticSearchService.fuzzySearch(approximateProductName);
        List<Hit<ProductES>> hitList = searchResponse.hits().hits();
        System.out.println(hitList);
        List<ProductES> productList = new ArrayList<>();
        for (Hit<ProductES> hit :hitList){
            productList.add(hit.source());
        }
        return productList;
    }



    @GetMapping
    public ResponseEntity<List<ProductSummaryResponse>> getAllProducts(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "perPage", defaultValue = "20") int perPage,
            @RequestParam(name = "q", required = false) String searchTerm,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "sort", defaultValue = "asc") String sort,
            @RequestParam(name = "subcategories", required = false) List<String> subCategories) {

        Pageable pageable;

        if ("desc".equalsIgnoreCase(sort)) {
            pageable = PageRequest.of(page - 1, perPage, Sort.by("price").descending());
        } else {
            pageable = PageRequest.of(page - 1, perPage, Sort.by("price").ascending());
        }

        Page<Product> productsPage;

        if (subCategories != null && !subCategories.isEmpty()) {
            productsPage = productRepository.findBySubCategoryIn(subCategories, pageable);

        } else if (searchTerm != null) {
            productsPage = productRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
        } else if (minPrice != null && maxPrice != null) {
            productsPage = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        } else {
            productsPage = productRepository.findAll(pageable);
        }

        List<Product> products = productsPage.getContent();


        List<ProductSummaryResponse> responseList = new ArrayList<>();

        for (Product product : products) {
            ProductSummaryResponse response = new ProductSummaryResponse();
            response.setId(product.getId());
            response.setName(product.getName());
            response.setPrice(product.getPrice());
            response.setImageURL(product.getImageURL());
            responseList.add(response);
        }

        return ResponseEntity.ok(responseList);
    }



    @GetMapping("/count")
    public ResponseEntity<Long> getProductCount() {
        long count = productRepository.count();
        return ResponseEntity.ok(count);
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
        newProduct.setPrice(productRequest.getPrice());
        newProduct.setQuantity(productRequest.getQuantity());
        newProduct.setImageURL(productRequest.getImageURL());


        Product savedProduct = productRepository.save(newProduct);

        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
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
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setImageURL(productRequest.getImageURL());




        Product updatedProduct = productRepository.save(existingProduct);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteProduct(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse(false, "Product not found."));
        }

        productRepository.deleteById(id);
        CustomResponse response = new CustomResponse(true, "Product deleted successfully");

        return ResponseEntity.ok(response);
    }



}
