package com.eCommercoal.eCommercialWeb.response;

import java.math.BigDecimal;
import java.util.List;

public class CartItemListResponse {
    private List<CartItemResponse> cartItems;
    private BigDecimal totalAmount;

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemResponse> cartItems) {
        this.cartItems = cartItems;
    }


    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public CartItemListResponse(List<CartItemResponse> cartItems, BigDecimal totalAmount) {
        this.cartItems = cartItems;
        this.totalAmount = totalAmount;
    }
}
