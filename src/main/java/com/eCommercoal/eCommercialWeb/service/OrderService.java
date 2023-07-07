package com.eCommercoal.eCommercialWeb.service;

import com.eCommercoal.eCommercialWeb.entity.CartItem;
import com.eCommercoal.eCommercialWeb.entity.Order;
import com.eCommercoal.eCommercialWeb.entity.Product;
import com.eCommercoal.eCommercialWeb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private ProductRepository productRepository;
    public void calculateTotalAmount(Order order) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : order.getCartItems()) {
            BigDecimal itemTotal = BigDecimal.valueOf(cartItem.getAmount()).multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setTotalAmount(totalAmount);
    }
    public void calculateCartItemAmount(CartItem cartItem) {
        float amount = getProductPrice(cartItem.getProductId()) * cartItem.getQuantity();
        cartItem.setAmount(amount);
    }
    private float getProductPrice(int productId) {
        // Fetch the product from the database using the provided productId
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return product.getPrice();
        } else {
            return 0.0f;
        }
    }

}
