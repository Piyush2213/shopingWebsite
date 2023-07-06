package com.eCommercoal.eCommercialWeb.repository;
import com.eCommercoal.eCommercialWeb.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findAllByUserId(Integer userId);
    Optional<CartItem> findByIdAndUserId(Integer itemId, Integer userId);
}
