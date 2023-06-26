package com.eCommercoal.eCommercialWeb.Exception;

public class customError {
    private String error;

    public customError(String error) {
        this.error = error;
    }
    public customError() {

    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
