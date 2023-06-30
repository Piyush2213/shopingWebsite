package com.eCommercoal.eCommercialWeb.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "eorder")
public class eOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String orderDescription;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private customer customer;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = shoppingCart.class)
    @JoinColumn(name = "eorder_id")
    private List<shoppingCart> cartItems;


    public eOrder(){};

    public eOrder(String orderDescription, customer customer, List<shoppingCart> cartItems) {
        this.orderDescription = orderDescription;
        this.customer = customer;
        this.cartItems = cartItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public customer getCustomer() {
        return customer;
    }

    public void setCustomer(customer customer) {
        this.customer = customer;
    }

    public List<shoppingCart> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<shoppingCart> cartItems) {
        this.cartItems = cartItems;
    }
}
