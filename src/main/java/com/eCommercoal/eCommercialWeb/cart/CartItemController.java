package com.eCommercoal.eCommercialWeb.cart;

import com.eCommercoal.eCommercialWeb.entity.CartItem;
import com.eCommercoal.eCommercialWeb.repository.CartRepository;
import com.eCommercoal.eCommercialWeb.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@RestController
@RequestMapping("/customer/{id}/carts/cartItems")

public class CartItemController {
    private final CartRepository cartRepository;
    private final CartService cartService;

    @Autowired
    public CartItemController(CartRepository cartRepository, CartService cartService) {
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartItem> createCartItem(@PathVariable Integer id, @RequestBody Map<String, Object> request) {
        int productId = (int) request.get("productId");
        int quantity = (int) request.get("quantity");

        Optional<CartItem> existingCartItem = cartRepository.findByProductIdAndUserId(productId, id);
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(quantity);
            cartService.calculateAmount(cartItem);
            CartItem updatedCartItem = cartRepository.save(cartItem);
            return ResponseEntity.ok(updatedCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(id);
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            cartService.calculateAmount(cartItem);
            CartItem createdCartItem = cartRepository.save(cartItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCartItem);
        }
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getAllCartItems(@PathVariable Integer id) {
        List<CartItem> cartItems = cartRepository.findAllByUserId(id);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<CartItem> getCartItem(@PathVariable Integer id, @PathVariable("itemId") Integer itemId) {
        Optional<CartItem> cartItem = cartRepository.findByIdAndUserId(itemId, id);
        if (cartItem.isPresent()) {
            return ResponseEntity.ok(cartItem.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Integer id, @PathVariable("itemId") Integer itemId, @RequestBody CartItem updatedCartItem) {
        Optional<CartItem> existingCartItem = cartRepository.findByIdAndUserId(itemId, id);
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setProductId(updatedCartItem.getProductId());
            cartItem.setQuantity(updatedCartItem.getQuantity());
            cartService.calculateAmount(cartItem);
            CartItem updatedItem = cartRepository.save(cartItem);
            return ResponseEntity.ok(updatedItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{itemId}")
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
