package com.eCommercoal.eCommercialWeb.request;

import java.math.BigDecimal;

public class CartItemRequest {

    private int productId;
    private int quantity;



    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public CartItemRequest( int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;

    }
    public CartItemRequest(){}
}
