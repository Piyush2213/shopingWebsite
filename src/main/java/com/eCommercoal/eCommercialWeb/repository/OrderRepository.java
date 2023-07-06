package com.eCommercoal.eCommercialWeb.repository;
import com.eCommercoal.eCommercialWeb.entity.Eorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Eorder,Integer> {

}
