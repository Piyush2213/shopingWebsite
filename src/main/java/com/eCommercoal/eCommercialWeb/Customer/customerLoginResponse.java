package com.eCommercoal.eCommercialWeb.Customer;

public class customerLoginResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public customerLoginResponse(String token) {
        this.token = token;
    }
}
