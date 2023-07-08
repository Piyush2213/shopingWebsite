package com.eCommercoal.eCommercialWeb.repository;
import com.eCommercoal.eCommercialWeb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findProductById(Integer id);
}
