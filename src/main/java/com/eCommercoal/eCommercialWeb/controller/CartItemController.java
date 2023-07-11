package com.eCommercoal.eCommercialWeb.controller;

import com.eCommercoal.eCommercialWeb.entity.CartItem;
import com.eCommercoal.eCommercialWeb.entity.Product;
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

public class CartItemController {
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public CartItemController(CartRepository cartRepository, CartService cartService, ProductRepository productRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @PostMapping("/cartItems")
    public ResponseEntity<CartItemResponse> createCartItem(@PathVariable Integer id, @RequestBody CartItemRequest request) {
        int productId = request.getProductId();
        int quantity = request.getQuantity();

        Optional<CartItem> existingCartItem = cartRepository.findByProductIdAndUserId(productId, id);
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(quantity);
            cartService.calculateAmount(cartItem);
            CartItem updatedCartItem = cartRepository.save(cartItem);

            Product product = productRepository.findById(productId).orElse(null);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            productService.updateProductQuantity(productId, quantity);

            CartItemResponse response = new CartItemResponse();
            response.setId(updatedCartItem.getId());
            response.setProductName(product.getName());
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

            Product product = productRepository.findById(productId).orElse(null);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            productService.updateProductQuantity(productId, quantity);

            CartItemResponse response = new CartItemResponse();
            response.setId(createdCartItem.getId());
            response.setProductName(product.getName());
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
