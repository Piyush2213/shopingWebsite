package com.eCommercoal.eCommercialWeb.controller;
import com.eCommercoal.eCommercialWeb.exception.ExistsException;
import com.eCommercoal.eCommercialWeb.request.CustomerLoginRequest;
import com.eCommercoal.eCommercialWeb.response.CustomerLoginResponse;
import com.eCommercoal.eCommercialWeb.entity.Customer;
import com.eCommercoal.eCommercialWeb.repository.CustomerRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"}, exposedHeaders = { "*" }, methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})

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
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        Customer customer = getUserFromToken(token);
        if (customer != null) {
            customerRepository.updateToken(customer.getId(), null);
            return ResponseEntity.ok().build();
        } else {
            throw new RuntimeException("Invalid token");
        }
    }
    private Customer getUserFromToken(String token) {
        Customer customer = customerRepository.findByToken(token);
        if (customer != null) {
            return customer;
        }
        throw new ExistsException("Invalid token or user not found.");
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
