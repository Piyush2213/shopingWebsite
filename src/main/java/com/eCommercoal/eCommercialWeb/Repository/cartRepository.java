package com.eCommercoal.eCommercialWeb.Repository;
import com.eCommercoal.eCommercialWeb.Entity.shoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface cartRepository extends JpaRepository<shoppingCart, Integer> {

}
