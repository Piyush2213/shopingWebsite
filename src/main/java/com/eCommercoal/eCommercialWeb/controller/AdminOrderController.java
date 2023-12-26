package com.eCommercoal.eCommercialWeb.controller;

import com.eCommercoal.eCommercialWeb.entity.Order;
import com.eCommercoal.eCommercialWeb.model.OrderStatus;
import com.eCommercoal.eCommercialWeb.repository.OrderRepository;
import com.eCommercoal.eCommercialWeb.service.OrderService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"}, exposedHeaders = {"*"}, methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
public class AdminOrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    @Autowired
    public AdminOrderController(OrderService orderService,OrderRepository orderRepository ){
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }
    private boolean isAdmin(String token) {
        try {
            System.out.println("Received Token: " + token);
            byte[] keyBytes = "adbashdgasgdahjshdjagsdgjhasdjahgdhasghjdgahjsgdhgsfvgavjvfgavytsdgavsdyasfgavgfvatyvdagbsgvfatyad".getBytes();
            System.out.println("key bytes: " + keyBytes);
            Claims claims = Jwts.parser()
                    .setSigningKey(keyBytes)
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("Token Claims: " + claims);

            String role = (String) claims.get("role");
            System.out.println("Role in Token: " + role);
            return "admin".equals(role);
        } catch (JwtException e) {
            System.err.println("Token Verification Failed: " + e.getMessage());
            return false;
        }
    }


    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Integer id, @RequestParam OrderStatus orderStatus, HttpServletRequest req) {
        String token = req.getHeader("Authorization");

        if (!isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. Admin privileges required.");
        }

        orderService.updateOrderStatus(id, orderStatus);
        return ResponseEntity.ok("Status updated successfully");
    }


    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Order>> getAllOrders(HttpServletRequest req) {
        try {
            String token = req.getHeader("Authorization");

            if (!isAdmin(token)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            List<Order> allOrders = orderService.getAllOrders();
            return ResponseEntity.ok(allOrders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
