package com.eCommercoal.eCommercialWeb.Repository;

import com.eCommercoal.eCommercialWeb.Dto.admin;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface adminRepository extends JpaRepository<admin, Integer> {
    admin findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE admin a SET a.token = ?2 WHERE a.id = ?1")
    int updateToken(int id, String token);

    @Query("SELECT a FROM admin a WHERE a.token = ?1")
    admin findByToken(String token);
}
