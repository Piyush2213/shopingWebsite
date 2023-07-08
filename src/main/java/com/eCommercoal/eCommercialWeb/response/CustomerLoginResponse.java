package com.eCommercoal.eCommercialWeb.response;

public class CustomerLoginResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CustomerLoginResponse(String token) {
        this.token = token;
    }
}
