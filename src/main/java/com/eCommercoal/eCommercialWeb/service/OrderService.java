package com.eCommercoal.eCommercialWeb.service;

import com.eCommercoal.eCommercialWeb.entity.CartItem;
import com.eCommercoal.eCommercialWeb.entity.Order;
import com.eCommercoal.eCommercialWeb.model.OrderStatus;
import com.eCommercoal.eCommercialWeb.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal amount = cartItem.getAmount();
            totalAmount = totalAmount.add(amount);
        }
        return totalAmount;
    }



     public void updateOrderStatus(Integer id, OrderStatus orderStatus){
        Order updateOrder = orderRepository.findById(id).orElse(null);
        updateOrder.setOrderStatus(orderStatus);
        orderRepository.save(updateOrder);
     }

    public List<Order> getAllOrders() {
        return orderRepository.findAllOrders();
    }



}
