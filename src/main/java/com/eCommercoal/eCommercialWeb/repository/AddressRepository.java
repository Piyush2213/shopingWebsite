package com.eCommercoal.eCommercialWeb.repository;

import com.eCommercoal.eCommercialWeb.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
