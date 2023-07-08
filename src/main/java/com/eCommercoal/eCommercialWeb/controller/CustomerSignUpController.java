package com.eCommercoal.eCommercialWeb.controller;
import com.eCommercoal.eCommercialWeb.request.CustomerSignUpRequest;
import com.eCommercoal.eCommercialWeb.entity.Customer;
import com.eCommercoal.eCommercialWeb.repository.CustomerRepository;
import com.eCommercoal.eCommercialWeb.response.CustomerSignUpResponse;
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
    public ResponseEntity<?> createNewEmployee(@RequestBody CustomerSignUpRequest customerSignUpRequest) {
        String email = customerSignUpRequest.getEmail();
        Customer existing = customerRepository.findByEmail(email);

        if (existing != null) {
            throw new RuntimeException("Customer with email " + email + " already exists.");
        }

        Customer newCustomer = new Customer();
        newCustomer.setFirstName(customerSignUpRequest.getFirstName());
        newCustomer.setLastName(customerSignUpRequest.getLastName());
        newCustomer.setEmail(email);
        newCustomer.setPassword(customerSignUpRequest.getPassword());
        newCustomer.setPhone(customerSignUpRequest.getPhone());
        newCustomer.setAddress(customerSignUpRequest.getAddress());


        Customer savedCustomer = customerRepository.save(newCustomer);

        CustomerSignUpResponse response = new CustomerSignUpResponse();
        response.setId(savedCustomer.getId());
        response.setFirstName(savedCustomer.getFirstName());
        response.setLastName(savedCustomer.getLastName());
        response.setEmail(savedCustomer.getEmail());
        response.setPhone(savedCustomer.getPhone());
        response.setAddress(savedCustomer.getAddress());

        return ResponseEntity.ok(response);
    }
}
