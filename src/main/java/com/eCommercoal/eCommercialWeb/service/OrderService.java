package com.eCommercoal.eCommercialWeb.service;

import com.eCommercoal.eCommercialWeb.entity.CartItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    public BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal amount = cartItem.getAmount();
            totalAmount = totalAmount.add(amount);
        }
        return totalAmount;
    }

}
