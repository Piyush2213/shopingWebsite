package com.eCommercoal.eCommercialWeb.Customer;
import com.eCommercoal.eCommercialWeb.Repository.customerRepository;
import com.eCommercoal.eCommercialWeb.Dto.customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class customerLoginController {
    @Autowired
    private customerRepository customerRepository;
    @PostMapping("/customer/login")
    public ResponseEntity<?> login(@RequestBody customerLogin request) {
        if (request.getToken() != null) {
            customer customer = customerRepository.findByToken(request.getToken());
            if (customer != null) {
                return ResponseEntity.ok(customer);
            }
        }

        customer customer = customerRepository.findByEmail(request.getEmail());
        if (customer == null || !customer.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }


        String token = generateToken(customer);

        return ResponseEntity.ok(new customerLoginResponse(token));
    }
    private String generateToken(customer customer) {
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
