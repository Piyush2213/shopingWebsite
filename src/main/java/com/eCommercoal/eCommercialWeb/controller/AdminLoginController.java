package com.eCommercoal.eCommercialWeb.controller;

import com.eCommercoal.eCommercialWeb.entity.Admin;
import com.eCommercoal.eCommercialWeb.exception.ExistsException;
import com.eCommercoal.eCommercialWeb.repository.AdminRepository;
import com.eCommercoal.eCommercialWeb.request.AdminLoginRequest;

import com.eCommercoal.eCommercialWeb.response.AdminLoginResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"}, exposedHeaders = { "*" }, methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})

public class AdminLoginController {

    @Autowired
    private AdminRepository adminRepository;


    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(@RequestBody AdminLoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail());
        if (admin == null || !admin.getPassword().equals(request.getPassword())) {
            throw new ExistsException("Invalid email or password");
        }


        String token = generateToken(admin);

        String capitalizedFirstName = capitalizeFirstLetter(admin.getName());
        AdminLoginResponse response = new AdminLoginResponse(token, capitalizedFirstName);

        return ResponseEntity.ok(response);
    }
    private String generateToken(Admin admin) {
        byte[] keyBytes = "adbashdgasgdahjshdjagsdgjhasdjahgdhasghjdgahjsgdhgsfvgavjvfgavytsdgavsdyasfgavgfvatyvdagbsgvfatyad".getBytes();
        String name = capitalizeFirstLetter(admin.getName());
        String token = Jwts.builder()

                .setSubject(admin.getEmail())
                .claim("name", admin.getName())
                .claim("role", "admin")
                .signWith(SignatureAlgorithm.HS512, keyBytes)
                .compact();

        admin.setToken(token);
        adminRepository.updateToken(admin.getId(), token);

        return token;
    }
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
