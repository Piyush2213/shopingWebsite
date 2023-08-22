package com.eCommercoal.eCommercialWeb.response;

public class CustomerLoginResponse {

    private String token;
    private String FirstName;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public CustomerLoginResponse(String token, String firstName) {
        this.token = token;
        FirstName = firstName;
    }
}
