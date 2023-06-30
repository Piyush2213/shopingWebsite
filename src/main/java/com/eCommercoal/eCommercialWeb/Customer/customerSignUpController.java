package com.eCommercoal.eCommercialWeb.Customer;
import com.eCommercoal.eCommercialWeb.Entity.customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class customerSignUpController {
    @Autowired
    private com.eCommercoal.eCommercialWeb.Repository.customerRepository customerRepository;
    @PostMapping("/customer/signup")
    public ResponseEntity<?> createNewEmployee(@RequestBody customerSignUp customerSignUp) {
        String email = customerSignUp.getEmail();
        customer existing = customerRepository.findByEmail(email);

        if (existing != null) {
            throw new RuntimeException("Customer with email " + email + " already exists.");
        }

        customer newCustomer = new customer();
        newCustomer.setFirstName(customerSignUp.getFirstName());
        newCustomer.setLastName(customerSignUp.getLastName());
        newCustomer.setEmail(email);
        newCustomer.setPassword(customerSignUp.getPassword());
        newCustomer.setPhone(customerSignUp.getPhone());
        newCustomer.setAddress(customerSignUp.getAddress());


        customer savedCustomer = customerRepository.save(newCustomer);

        return ResponseEntity.ok(savedCustomer);
    }
}
