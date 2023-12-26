package com.eCommercoal.eCommercialWeb.repository;

import com.eCommercoal.eCommercialWeb.entity.Customer;
import com.eCommercoal.eCommercialWeb.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByCustomerId(Integer customerId);
    @Query("SELECT o FROM Order o")
    List<Order> findAllOrders();
}
