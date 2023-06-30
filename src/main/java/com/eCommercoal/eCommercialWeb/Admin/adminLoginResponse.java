package com.eCommercoal.eCommercialWeb.Admin;

public class adminLoginResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public adminLoginResponse(String token) {
        this.token = token;
    }
}
