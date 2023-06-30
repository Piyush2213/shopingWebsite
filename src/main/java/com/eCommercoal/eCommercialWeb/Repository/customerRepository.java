package com.eCommercoal.eCommercialWeb.Repository;
import com.eCommercoal.eCommercialWeb.Entity.customer;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface customerRepository extends JpaRepository<customer, Integer> {
    @Query("SELECT c FROM customer c WHERE c.email = ?1")
    customer findByEmail(String email);
    public customer getCustomerByEmailAndName(String email, String name);

    @Transactional
    @Modifying
    @Query("UPDATE customer c SET c.token = ?2 WHERE c.id = ?1")
    int updateToken(int id, String token);

    @Query("SELECT c FROM customer c WHERE c.token = ?1")
    customer findByToken(String token);
}
