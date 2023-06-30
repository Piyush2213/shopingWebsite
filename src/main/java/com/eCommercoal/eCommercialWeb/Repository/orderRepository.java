package com.eCommercoal.eCommercialWeb.Repository;
import com.eCommercoal.eCommercialWeb.Entity.eOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface orderRepository extends JpaRepository<eOrder,Integer> {

}
