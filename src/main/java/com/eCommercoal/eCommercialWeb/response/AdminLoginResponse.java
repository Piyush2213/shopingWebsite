package com.eCommercoal.eCommercialWeb.response;


public class AdminLoginResponse {
    private String token;
    private String name;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdminLoginResponse(String token, String name) {
        this.token = token;
        this.name = name;
    }
}
