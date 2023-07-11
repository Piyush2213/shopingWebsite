package com.eCommercoal.eCommercialWeb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerControllerAdvice {
    @ExceptionHandler(ExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public CustomError handleCustomerExistsException(ExistsException ex) {
        return new CustomError(ex.getMessage());
    }
}
