package com.eCommercoal.eCommercialWeb.Service;

import org.springframework.stereotype.Service;
import com.eCommercoal.eCommercialWeb.Entity.customer;
import com.eCommercoal.eCommercialWeb.Repository.customerRepository;

@Service
public class customerService {
    private customerRepository customerRepository;

    public customerService(customerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public customer saveCustomer(customer customer){
        return customerRepository.save(customer);
    }

    public Integer isCustomerPresent(customer customer){
        customer customer1 = customerRepository.getCustomerByEmailAndName(customer.getEmail(),customer.getFirstName());
        return customer1!=null ? customer1.getId(): null ;
    }
}
