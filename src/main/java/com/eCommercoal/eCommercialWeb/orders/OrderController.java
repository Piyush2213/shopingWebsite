package com.eCommercoal.eCommercialWeb.orders;

import com.eCommercoal.eCommercialWeb.entity.CartItem;
import com.eCommercoal.eCommercialWeb.entity.Customer;
import com.eCommercoal.eCommercialWeb.entity.Order;
import com.eCommercoal.eCommercialWeb.orderRequest.CartItemRequest;
import com.eCommercoal.eCommercialWeb.orderRequest.OrderRequest;
import com.eCommercoal.eCommercialWeb.repository.OrderRepository;
import com.eCommercoal.eCommercialWeb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @PostMapping("/customer/{userId}")
    public ResponseEntity<Order> createOrder(@PathVariable int userId, @RequestBody OrderRequest orderRequest) {
        Customer customer = new Customer();
        customer.setId(userId);

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDescription(orderRequest.getOrderDescription());

        List<CartItem> cartItems = new ArrayList<>();
        for (CartItemRequest cartItemRequest : orderRequest.getCartItems()) {
            CartItem cartItem = convertToCartItem(cartItemRequest);
            orderService.calculateCartItemAmount(cartItem);
            cartItems.add(cartItem);
        }
        order.setCartItems(cartItems);

        orderService.calculateTotalAmount(order);
        Order createdOrder = orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    private CartItem convertToCartItem(CartItemRequest cartItemRequest) {
        CartItem cartItem = new CartItem();
        cartItem.setProductId(cartItemRequest.getProductId());
        cartItem.setQuantity(cartItemRequest.getQuantity());
        return cartItem;
    }
}