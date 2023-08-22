package com.eCommercoal.eCommercialWeb.controller;

import com.eCommercoal.eCommercialWeb.entity.CartItem;
import com.eCommercoal.eCommercialWeb.entity.Customer;
import com.eCommercoal.eCommercialWeb.entity.Product;
import com.eCommercoal.eCommercialWeb.exception.CustomError;
import com.eCommercoal.eCommercialWeb.exception.ExistsException;
import com.eCommercoal.eCommercialWeb.repository.CustomerRepository;
import com.eCommercoal.eCommercialWeb.repository.ProductRepository;
import com.eCommercoal.eCommercialWeb.request.CartItemRequest;
import com.eCommercoal.eCommercialWeb.repository.CartRepository;
import com.eCommercoal.eCommercialWeb.response.CartItemCreateResponse;
import com.eCommercoal.eCommercialWeb.response.CartItemListResponse;
import com.eCommercoal.eCommercialWeb.response.CartItemResponse;
import com.eCommercoal.eCommercialWeb.service.CartService;
import com.eCommercoal.eCommercialWeb.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carts")
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"}, exposedHeaders = {"*"}, methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
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

    private Customer getUserFromToken(String token) {
        Customer customer = customerRepository.findByToken(token);
        if (customer != null) {
            return customer;
        }
        throw new ExistsException("Invalid token or user not found.");
    }

    @PostMapping("/cartItems")
    public ResponseEntity<CartItemCreateResponse> createCartItem(HttpServletRequest req, @RequestBody CartItemRequest request) {
        String token = req.getHeader("Authorization");
        Customer customer = getUserFromToken(token);

        if (customer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        int id = customer.getId();
        int productId = request.getProductId();
        int quantity = request.getQuantity();

        if (productId <= 0 || quantity <= 0) {
            throw new ExistsException("Wrong Product Id entered...");

        }

        Product existingProduct = productRepository.findById(productId).orElse(null);
        if (existingProduct == null) {
            throw new ExistsException("Product with ID " + productId + " not found.");
        }

        Optional<CartItem> existingCartItem = cartRepository.findByProductIdAndUserId(productId, id);
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(quantity);
            cartService.calculateAmount(cartItem);
            CartItem updatedCartItem = cartRepository.save(cartItem);

            CartItemCreateResponse response = new CartItemCreateResponse();
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

            CartItemCreateResponse response = new CartItemCreateResponse();
            response.setId(createdCartItem.getId());
            response.setProductName(existingProduct.getName());
            response.setQuantity(createdCartItem.getQuantity());
            response.setAmount(createdCartItem.getAmount());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
    }




    @GetMapping("/cartItems")
    public ResponseEntity<CartItemListResponse> getAllCartItems(HttpServletRequest req) {
        String token = req.getHeader("Authorization");

        try {
            Customer customer = getUserFromToken(token);
            int id = customer.getId();

            List<CartItem> cartItems = cartRepository.findAllByUserId(id);
            List<CartItemResponse> cartItemResponses = new ArrayList<>();

            BigDecimal totalAmount = BigDecimal.ZERO;

            for (CartItem cartItem : cartItems) {
                Product product = productRepository.findById(cartItem.getProductId()).orElse(null);
                if (product != null) {
                    cartService.calculateAmount(cartItem);
                    CartItemResponse response = new CartItemResponse();
                    response.setId(cartItem.getId());
                    response.setProductId(product.getId());
                    response.setImageURL(product.getImageURL());
                    response.setProductName(product.getName());
                    response.setQuantity(cartItem.getQuantity());
                    response.setAmount(cartItem.getAmount());
                    cartItemResponses.add(response);

                    totalAmount = totalAmount.add(cartItem.getAmount());
                }
            }

            CartItemListResponse cartItemListResponse = new CartItemListResponse(cartItemResponses, totalAmount);

            return ResponseEntity.ok(cartItemListResponse);
        } catch (ExistsException ex) {
            throw new ExistsException("Invalid token or user not found.");
        }
    }



    @GetMapping("/cartItems/{itemId}")
    public ResponseEntity<CartItemResponse> getCartItem(HttpServletRequest req, @PathVariable("itemId") Integer itemId) {
        String token = req.getHeader("Authorization");

        try {
            Customer customer = getUserFromToken(token);
            int userId = customer.getId();

            Optional<CartItem> cartItem = cartRepository.findByIdAndUserId(itemId, userId);
            if (cartItem.isPresent()) {
                CartItem existingCartItem = cartItem.get();
                cartService.calculateAmount(existingCartItem);
                Product product = productRepository.findById(existingCartItem.getProductId()).orElse(null);
                if (product != null) {
                    CartItemResponse response = new CartItemResponse();
                    response.setId(existingCartItem.getId());
                    response.setProductId(product.getId());
                    response.setImageURL(product.getImageURL());
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
        } catch (ExistsException ex) {
            throw new ExistsException("Invalid token or user not found.");

        }
    }


    @PutMapping("/cartItems/{itemId}")
    public ResponseEntity<CartItemResponse> updateCartItem(HttpServletRequest req, @PathVariable("itemId") Integer itemId, @RequestBody CartItem updatedCartItem) {
        String token = req.getHeader("Authorization");

        try {
            Customer customer = getUserFromToken(token);
            int userId = customer.getId();

            Optional<CartItem> existingCartItem = cartRepository.findByIdAndUserId(itemId, userId);
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
        } catch (ExistsException ex) {
            throw new ExistsException("Invalid token or user not found.");

        }
    }

    @DeleteMapping("/cartItems/{itemId}")
    public ResponseEntity<Void> deleteCartItem(HttpServletRequest req, @PathVariable("itemId") Integer itemId) {
        String token = req.getHeader("Authorization");

        try {
            Customer customer = getUserFromToken(token);
            int userId = customer.getId();

            Optional<CartItem> existingCartItem = cartRepository.findByIdAndUserId(itemId, userId);
            if (existingCartItem.isPresent()) {
                cartRepository.delete(existingCartItem.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ExistsException ex) {
            throw new ExistsException("Invalid token or user not found.");
        }
    }

}
