package com.eCommercoal.eCommercialWeb.Admin;

import com.eCommercoal.eCommercialWeb.Dto.admin;
import com.eCommercoal.eCommercialWeb.Repository.adminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class signUpController {

    @Autowired
    private adminRepository adminRepository;
    @PostMapping("/admin/signup")
    public ResponseEntity<?> createNewAdmin(@RequestBody adminSignUp adminSignUp) {
        String email = adminSignUp.getEmail();
        admin existingAdmin = adminRepository.findByEmail(email);

        if (existingAdmin != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Admin with email " + email + " already exists.");
        }

        admin newAdmin = new admin();
        newAdmin.setName(adminSignUp.getName());
        newAdmin.setEmail(adminSignUp.getEmail());
        newAdmin.setPassword(adminSignUp.getPassword());

        admin savedAdmin = adminRepository.save(newAdmin);

        return ResponseEntity.ok(savedAdmin);
    }
}
