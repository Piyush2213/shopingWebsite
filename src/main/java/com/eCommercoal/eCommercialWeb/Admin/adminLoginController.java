package com.eCommercoal.eCommercialWeb.Admin;

import com.eCommercoal.eCommercialWeb.Dto.admin;
import com.eCommercoal.eCommercialWeb.Repository.adminRepository;
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
public class adminLoginController {
    @Autowired
    private adminRepository adminRepository;
    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody adminLogin request) {
        if (request.getToken() != null) {
            admin admin = adminRepository.findByToken(request.getToken());
            if (admin != null) {
                return ResponseEntity.ok(admin);
            }
        }

        admin admin = adminRepository.findByEmail(request.getEmail());
        if (admin == null || !admin.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        String token = generateToken(admin);

        return ResponseEntity.ok(new adminLoginResponse(token));
    }


    private String generateToken(admin admin) {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();

        String token = Jwts.builder()
                .setSubject(admin.getEmail())
                .signWith(SignatureAlgorithm.HS512, keyBytes)
                .compact();

        admin.setToken(token);
        adminRepository.updateToken(admin.getId(), token);

        return token;
    }
}
