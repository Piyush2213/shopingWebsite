package com.eCommercoal.eCommercialWeb.Cart;

import org.slf4j.LoggerFactory;
import com.eCommercoal.eCommercialWeb.Entity.product;
import com.eCommercoal.eCommercialWeb.Entity.eOrder;
import com.eCommercoal.eCommercialWeb.Entity.customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eCommercoal.eCommercialWeb.Service.orderService;
import com.eCommercoal.eCommercialWeb.Service.productService;
import com.eCommercoal.eCommercialWeb.Service.customerService;
import com.eCommercoal.eCommercialWeb.OrderResponse.responseOrderDTO;
import com.eCommercoal.eCommercialWeb.OrderResponse.orderDTO;
import com.eCommercoal.eCommercialWeb.Util.dateUtil;


import java.util.List;
import java.util.Random;
import org.slf4j.Logger;


@RestController
@RequestMapping("/api")
public class shoppingCartController {
    private orderService orderService;
    private productService productService;
    private customerService customerService;


    public shoppingCartController(orderService orderService, productService productService, customerService customerService) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
    }

    private Logger logger = LoggerFactory.getLogger(shoppingCartController.class);

    @GetMapping(value = "/getAllProducts")
    public ResponseEntity<List<product>> getAllProducts() {

        List<product> productList = productService.getAllProducts();

        return ResponseEntity.ok(productList);
    }

    @GetMapping(value = "/getOrder/{orderId}")
    public ResponseEntity<eOrder> getOrderDetails(@PathVariable int orderId) {

        eOrder order = orderService.getOrderDetail(orderId);
        return ResponseEntity.ok(order);
    }


    @PostMapping("/placeOrder")
    public ResponseEntity<responseOrderDTO> placeOrder(@RequestBody orderDTO orderDTO) {
        logger.info("Request Payload " + orderDTO.toString());
        responseOrderDTO responseOrderDTO = new responseOrderDTO();
        float amount = orderService.getCartAmount(orderDTO.getCartItems());

        customer customer = new customer(orderDTO.getCustomerName(), orderDTO.getCustomerEmail());
        Integer customerIdFromDb = customerService.isCustomerPresent(customer);
        if (customerIdFromDb != null) {
            customer.setId(customerIdFromDb);
            logger.info("Customer already present in db with id : " + customerIdFromDb);
        }else{
            customer = customerService.saveCustomer(customer);
            logger.info("Customer saved.. with id : " + customer.getId());
        }
        eOrder order = new eOrder(orderDTO.getOrderDescription(), customer, orderDTO.getCartItems());
        order = orderService.saveOrder(order);
        logger.info("Order processed successfully..");

        responseOrderDTO.setAmount(amount);
        responseOrderDTO.setDate(dateUtil.getCurrentDateTime());
        responseOrderDTO.setInvoiceNumber(new Random().nextInt(1000));
        responseOrderDTO.setOrderId(order.getId());
        responseOrderDTO.setOrderDescription(orderDTO.getOrderDescription());

        logger.info("test push..");

        return ResponseEntity.ok(responseOrderDTO);
    }


}
