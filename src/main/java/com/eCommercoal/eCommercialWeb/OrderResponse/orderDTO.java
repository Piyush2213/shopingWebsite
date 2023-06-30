package com.eCommercoal.eCommercialWeb.OrderResponse;
import com.eCommercoal.eCommercialWeb.Entity.shoppingCart;

import java.util.List;

public class orderDTO {
    private String orderDescription;
    private List<shoppingCart> cartItems;
    private String customerEmail;
    private String customerName;

    public orderDTO() {
    }

    public orderDTO(String orderDescription, List<shoppingCart> cartItems, String customerEmail, String customerName) {
        this.orderDescription = orderDescription;
        this.cartItems = cartItems;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
    }
    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public List<shoppingCart> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<shoppingCart> cartItems) {
        this.cartItems = cartItems;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderDescription='" + orderDescription + '\'' +
                ", cartItems=" + cartItems +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
