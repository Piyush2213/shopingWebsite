package com.eCommercoal.eCommercialWeb.controller;

import com.eCommercoal.eCommercialWeb.entity.CartItem;
import com.eCommercoal.eCommercialWeb.entity.Customer;
import com.eCommercoal.eCommercialWeb.entity.Product;
import com.eCommercoal.eCommercialWeb.exception.CustomError;
import com.eCommercoal.eCommercialWeb.repository.CustomerRepository;
import com.eCommercoal.eCommercialWeb.repository.ProductRepository;
import com.eCommercoal.eCommercialWeb.request.CartItemRequest;
import com.eCommercoal.eCommercialWeb.repository.CartRepository;
import com.eCommercoal.eCommercialWeb.response.CartItemResponse;
import com.eCommercoal.eCommercialWeb.service.CartService;
import com.eCommercoal.eCommercialWeb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/customer/{id}/carts")
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"}, exposedHeaders = { "*" }, methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})


public class CartItemController {
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;
    private final ProductService productService;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    public CartItemController(CartRepository cartRepository, CartService cartService, ProductRepository productRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @PostMapping("/cartItems")
    public ResponseEntity<CartItemResponse> createCartItem(@PathVariable Integer id, @RequestBody CartItemRequest request) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (!existingCustomer.isPresent()) {
            CustomError error = new CustomError("Customer with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        int productId = request.getProductId();
        int quantity = request.getQuantity();

        Product existingProduct = productRepository.findById(productId).orElse(null);
        if (existingProduct == null) {
            CustomError error = new CustomError("Product with ID " + productId + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Optional<CartItem> existingCartItem = cartRepository.findByProductIdAndUserId(productId, id);
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(quantity);
            cartService.calculateAmount(cartItem);
            CartItem updatedCartItem = cartRepository.save(cartItem);

            CartItemResponse response = new CartItemResponse();
            response.setId(updatedCartItem.getId());
            response.setProductName(existingProduct.getName());
            response.setQuantity(updatedCartItem.getQuantity());
            response.setAmount(updatedCartItem.getAmount());

            return ResponseEntity.ok(response);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(id);
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            cartService.calculateAmount(cartItem);
            CartItem createdCartItem = cartRepository.save(cartItem);

            CartItemResponse response = new CartItemResponse();
            response.setId(createdCartItem.getId());
            response.setProductName(existingProduct.getName());
            response.setQuantity(createdCartItem.getQuantity());
            response.setAmount(createdCartItem.getAmount());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
    }


    @GetMapping("/cartItems")
    public ResponseEntity<List<CartItemResponse>> getAllCartItems(@PathVariable Integer id) {
        List<CartItem> cartItems = cartRepository.findAllByUserId(id);
        List<CartItemResponse> cartItemResponses = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProductId()).orElse(null);
            if (product != null) {
                cartService.calculateAmount(cartItem);
                CartItemResponse response = new CartItemResponse();
                response.setId(cartItem.getId());
                response.setProductName(product.getName());
                response.setQuantity(cartItem.getQuantity());
                response.setAmount(cartItem.getAmount());
                cartItemResponses.add(response);

                totalAmount = totalAmount.add(cartItem.getAmount());
            }
        }

        for (CartItemResponse response : cartItemResponses) {
            response.setTotalAmount(totalAmount);
        }

        return ResponseEntity.ok(cartItemResponses);
    }



    @GetMapping("/cartItems/{itemId}")
    public ResponseEntity<CartItemResponse> getCartItem(@PathVariable Integer id, @PathVariable("itemId") Integer itemId) {
        Optional<CartItem> cartItem = cartRepository.findByIdAndUserId(itemId, id);
        if (cartItem.isPresent()) {
            CartItem existingCartItem = cartItem.get();
            cartService.calculateAmount(existingCartItem);
            Product product = productRepository.findById(existingCartItem.getProductId()).orElse(null);
            if (product != null) {
                CartItemResponse response = new CartItemResponse();
                response.setId(existingCartItem.getId());
                response.setProductName(product.getName());
                response.setQuantity(existingCartItem.getQuantity());
                response.setAmount(existingCartItem.getAmount());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("/cartItems/{itemId}")
    public ResponseEntity<CartItemResponse> updateCartItem(@PathVariable Integer id, @PathVariable("itemId") Integer itemId, @RequestBody CartItem updatedCartItem) {
        Optional<CartItem> existingCartItem = cartRepository.findByIdAndUserId(itemId, id);
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setProductId(updatedCartItem.getProductId());
            cartItem.setQuantity(updatedCartItem.getQuantity());
            cartService.calculateAmount(cartItem);
            CartItem updatedItem = cartRepository.save(cartItem);

            Product product = productRepository.findById(updatedItem.getProductId()).orElse(null);
            if (product != null) {
                CartItemResponse response = new CartItemResponse();
                response.setId(updatedItem.getId());
                response.setProductName(product.getName());
                response.setQuantity(updatedItem.getQuantity());
                response.setAmount(updatedItem.getAmount());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/cartItems/{itemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Integer id, @PathVariable("itemId") Integer itemId) {
        Optional<CartItem> existingCartItem = cartRepository.findByIdAndUserId(itemId, id);
        if (existingCartItem.isPresent()) {
            cartRepository.delete(existingCartItem.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
