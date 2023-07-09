package com.eCommercoal.eCommercialWeb.request;

public class OrderRequest {
    private String orderDescription;

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public OrderRequest(String orderDescription) {
        this.orderDescription = orderDescription;
    }
    public OrderRequest(){};
}
