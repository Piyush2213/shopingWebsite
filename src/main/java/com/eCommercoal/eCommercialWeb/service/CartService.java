package com.eCommercoal.eCommercialWeb.service;


import com.eCommercoal.eCommercialWeb.entity.CartItem;
import com.eCommercoal.eCommercialWeb.entity.Product;
import com.eCommercoal.eCommercialWeb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {
    private final ProductRepository productRepository;

    @Autowired
    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void calculateAmount(CartItem cartItem) {
        int productId = cartItem.getProductId();
        int quantity = cartItem.getQuantity();

        Product product = productRepository.findById(productId).orElse(null);

        if (product != null) {
            BigDecimal productPrice = product.getPrice();
            BigDecimal amount = productPrice.multiply(BigDecimal.valueOf(quantity));
            cartItem.setAmount(amount);
        } else {
            cartItem.setAmount(BigDecimal.ZERO);
        }
    }


    public BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            totalAmount = totalAmount.add(cartItem.getAmount());
        }
        return totalAmount;
    }

}
