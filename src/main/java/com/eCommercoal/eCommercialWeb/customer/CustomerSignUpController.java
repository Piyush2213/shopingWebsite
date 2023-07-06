package com.eCommercoal.eCommercialWeb.customer;
import com.eCommercoal.eCommercialWeb.entity.Customer;
import com.eCommercoal.eCommercialWeb.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerSignUpController {
    @Autowired
    private CustomerRepository customerRepository;
    @PostMapping("/signup")
    public ResponseEntity<?> createNewEmployee(@RequestBody CustomerSignUp customerSignUp) {
        String email = customerSignUp.getEmail();
        Customer existing = customerRepository.findByEmail(email);

        if (existing != null) {
            throw new RuntimeException("Customer with email " + email + " already exists.");
        }

        Customer newCustomer = new Customer();
        newCustomer.setFirstName(customerSignUp.getFirstName());
        newCustomer.setLastName(customerSignUp.getLastName());
        newCustomer.setEmail(email);
        newCustomer.setPassword(customerSignUp.getPassword());
        newCustomer.setPhone(customerSignUp.getPhone());
        newCustomer.setAddress(customerSignUp.getAddress());


        Customer savedCustomer = customerRepository.save(newCustomer);

        return ResponseEntity.ok(savedCustomer);
    }
}
