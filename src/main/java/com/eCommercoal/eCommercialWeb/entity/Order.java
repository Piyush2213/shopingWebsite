package com.eCommercoal.eCommercialWeb.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "order_description")
    private String orderDescription;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CartItem> getOrderItems() {
        return cartItems;
    }

    public void setOrderItems(List<CartItem> orderItems) {
        this.cartItems = orderItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Order(Integer id, String orderDescription, Customer customer, List<CartItem> cartItems, BigDecimal totalAmount) {
        this.id = id;
        this.orderDescription = orderDescription;
        this.customer = customer;
        this.cartItems = cartItems;
        this.totalAmount = totalAmount;
    }
    public Order(){};
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
