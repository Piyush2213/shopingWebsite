package com.eCommercoal.eCommercialWeb.repository;
import com.eCommercoal.eCommercialWeb.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByCustomerId(int customerId);
    Order findByIdAndCustomerId(int orderId, int customerId);


}
