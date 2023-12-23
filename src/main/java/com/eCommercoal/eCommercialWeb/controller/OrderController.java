package com.eCommercoal.eCommercialWeb.controller;

import com.eCommercoal.eCommercialWeb.entity.*;
import com.eCommercoal.eCommercialWeb.exception.ExistsException;
import com.eCommercoal.eCommercialWeb.repository.*;
import com.eCommercoal.eCommercialWeb.response.OrderItemResponse;
import com.eCommercoal.eCommercialWeb.response.OrderResponse;
import com.eCommercoal.eCommercialWeb.service.CartService;
import com.eCommercoal.eCommercialWeb.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"}, exposedHeaders = {"*"}, methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})

public class OrderController {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final OrderService orderService;
    private final CartService cartService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, CartRepository cartRepository, CustomerRepository customerRepository, OrderService orderService, CartService cartService, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.orderService = orderService;
        this.cartService = cartService;
        this.addressRepository = addressRepository;
    }
    private Customer getUserFromToken(String token) {
        Customer customer = customerRepository.findByToken(token);
        if (customer != null) {
            return customer;
        }
        throw new ExistsException("Invalid token or user not found.");
    }




    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody Address address, HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        Customer customer = getUserFromToken(token);



        if (customer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<CartItem> cartItems = cartRepository.findAllByUserId(customer.getId());
        if (cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }




        Address orderAddress = new Address();
        orderAddress.setHouseNo(address.getHouseNo());
        orderAddress.setStreet(address.getStreet());
        orderAddress.setCity(address.getCity());
        orderAddress.setPin(address.getPin());
        orderAddress.setCustomer(customer);



        Address savedAddress = addressRepository.save(orderAddress);


        Order order = new Order();
        order.setCustomer(customer);
        order.setTotalAmount(BigDecimal.ZERO);
        order.setDeliveryAddress(savedAddress);


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


        List<OrderItemResponse> orderedProductsList = new ArrayList<>();
        for (OrderItem orderItem : createdOrder.getOrderItems()) {
            OrderItemResponse orderedProduct = new OrderItemResponse();
            orderedProduct.setProductId(orderItem.getProductId());
            orderedProduct.setQuantity(orderItem.getQuantity());
            orderedProductsList.add(orderedProduct);
        }

        OrderResponse response = new OrderResponse();
        response.setId(createdOrder.getId());
        response.setTotalAmount(createdOrder.getTotalAmount());
        response.setCustomerId(createdOrder.getCustomer().getId());
        response.setOrderItems(orderedProductsList);
        response.setDateTime(createdOrder.getDateTime());
        response.setDeliveryAddress(savedAddress);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }





    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        Customer customer = getUserFromToken(token);

        if (customer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Order> orders = orderRepository.findAllByCustomerId(customer.getId());
        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItemResponse> orderedProductsList = new ArrayList<>();
            for (OrderItem orderItem : order.getOrderItems()) {

                OrderItemResponse orderedProduct = new OrderItemResponse();
                orderedProduct.setProductId(orderItem.getProductId());
                orderedProduct.setQuantity(orderItem.getQuantity());
                Product product = productRepository.findById(orderItem.getProductId()).orElse(null);
                if (product != null) {
                    orderedProduct.setProductId(product.getId());
                    orderedProduct.setProductName(product.getName());
                    orderedProduct.setProductImageURL(product.getImageURL());
                    orderedProduct.setPrice(product.getPrice());
                }
                orderedProductsList.add(orderedProduct);
            }

            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(order.getId());
            orderResponse.setTotalAmount(order.getTotalAmount());
            orderResponse.setCustomerId(order.getCustomer().getId());
            orderResponse.setOrderItems(orderedProductsList);
            orderResponse.setDateTime(order.getDateTime());

            orderResponses.add(orderResponse);
        }

        return ResponseEntity.ok(orderResponses);
    }



}