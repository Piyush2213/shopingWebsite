package com.eCommercoal.eCommercialWeb.controller;
import com.eCommercoal.eCommercialWeb.exception.ExistsException;
import com.eCommercoal.eCommercialWeb.request.CustomerLoginRequest;
import com.eCommercoal.eCommercialWeb.response.CustomerLoginResponse;
import com.eCommercoal.eCommercialWeb.entity.Customer;
import com.eCommercoal.eCommercialWeb.repository.CustomerRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerLoginController {
    @Autowired
    private CustomerRepository customerRepository;
    @PostMapping("/login")
    public ResponseEntity<CustomerLoginResponse> login(@RequestBody CustomerLoginRequest request) {
        Customer customer = customerRepository.findByEmail(request.getEmail());
        if (customer == null || !customer.getPassword().equals(request.getPassword())) {
            throw new ExistsException("Invalid email or password");
        }


        String token = generateToken(customer);

        return ResponseEntity.ok(new CustomerLoginResponse(token));
    }
    private String generateToken(Customer customer) {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();

        String token = Jwts.builder()
                .setSubject(customer.getEmail())
                .signWith(SignatureAlgorithm.HS512, keyBytes)
                .compact();

        customer.setToken(token);
        customerRepository.updateToken(customer.getId(), token);

        return token;
    }
}
