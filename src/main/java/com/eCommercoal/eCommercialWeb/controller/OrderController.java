package com.eCommercoal.eCommercialWeb.controller;

import com.eCommercoal.eCommercialWeb.entity.CartItem;
import com.eCommercoal.eCommercialWeb.entity.Customer;
import com.eCommercoal.eCommercialWeb.entity.Order;
import com.eCommercoal.eCommercialWeb.entity.OrderItem;
import com.eCommercoal.eCommercialWeb.repository.CartRepository;
import com.eCommercoal.eCommercialWeb.repository.CustomerRepository;
import com.eCommercoal.eCommercialWeb.repository.OrderRepository;
import com.eCommercoal.eCommercialWeb.request.OrderRequest;
import com.eCommercoal.eCommercialWeb.response.OrderResponse;
import com.eCommercoal.eCommercialWeb.service.CartService;
import com.eCommercoal.eCommercialWeb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer/{customerId}/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final CartService cartService;

    @Autowired
    public OrderController(OrderRepository orderRepository, CartRepository cartRepository, CustomerRepository customerRepository, OrderService orderService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@PathVariable Integer customerId, @RequestBody OrderRequest orderRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Customer customer = optionalCustomer.get();
        List<CartItem> cartItems = cartRepository.findAllByUserId(customerId);

        if (cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }



        Order order = new Order();
        order.setOrderDescription(orderRequest.getOrderDescription());
        order.setCustomer(customer);
        order.setTotalAmount(BigDecimal.ZERO);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            cartService.calculateAmount(cartItem);
            cartItem.setOrder(order);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);

            totalAmount = totalAmount.add(cartItem.getAmount());
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        Clock cl = Clock.systemUTC();
        order.setDateTime(LocalDateTime.now(cl));

        Order createdOrder = orderRepository.save(order);

        cartRepository.deleteAll(cartItems);

        OrderResponse response = new OrderResponse();
        response.setId(createdOrder.getId());
        response.setOrderDescription(createdOrder.getOrderDescription());
        response.setTotalAmount(createdOrder.getTotalAmount());
        response.setCustomerId(createdOrder.getCustomer().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}