package com.eCommercoal.eCommercialWeb.controller;
import com.eCommercoal.eCommercialWeb.exception.ExistsException;
import com.eCommercoal.eCommercialWeb.request.CustomerLoginRequest;
import com.eCommercoal.eCommercialWeb.response.CustomerLoginResponse;
import com.eCommercoal.eCommercialWeb.entity.Customer;
import com.eCommercoal.eCommercialWeb.repository.CustomerRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
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

        String capitalizedFirstName = capitalizeFirstLetter(customer.getFirstName());
        CustomerLoginResponse response = new CustomerLoginResponse(token, capitalizedFirstName);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/logout")
    @Transactional
    public ResponseEntity<Void> logout(HttpServletRequest req,  HttpServletResponse res) {
        String token = req.getHeader("Authorization");
        Customer customer = getUserFromToken(token);
        if (customer != null) {
            customerRepository.updateToken(customer.getId(), null);
            Cookie tokenCookie = new Cookie("token", null);
            tokenCookie.setMaxAge(0);
            tokenCookie.setPath("/");
            res.addCookie(tokenCookie);
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
        String firstName = capitalizeFirstLetter(customer.getFirstName());
        String token = Jwts.builder()

                .setSubject(customer.getEmail())
                .claim("name", customer.getFirstName())
                .signWith(SignatureAlgorithm.HS512, keyBytes)
                .compact();

        customer.setToken(token);
        customerRepository.updateToken(customer.getId(), token);

        return token;
    }
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
