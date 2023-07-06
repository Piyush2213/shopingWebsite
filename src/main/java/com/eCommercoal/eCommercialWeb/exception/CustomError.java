package com.eCommercoal.eCommercialWeb.exception;

public class CustomError {
    private String error;

    public CustomError(String error) {
        this.error = error;
    }
    public CustomError() {

    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
