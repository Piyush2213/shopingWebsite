package com.eCommercoal.eCommercialWeb.Repository;
import com.eCommercoal.eCommercialWeb.Entity.product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface productRepository extends JpaRepository<product, Integer> {
    Optional<product> findProductById(Integer id);
}
