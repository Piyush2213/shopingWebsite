package com.eCommercoal.eCommercialWeb.Service;

import org.springframework.stereotype.Service;
import com.eCommercoal.eCommercialWeb.Entity.eOrder;
import com.eCommercoal.eCommercialWeb.Entity.shoppingCart;
import com.eCommercoal.eCommercialWeb.Entity.product;
import com.eCommercoal.eCommercialWeb.Repository.orderRepository;
import com.eCommercoal.eCommercialWeb.Repository.productRepository;

import java.util.List;
import java.util.Optional;

@Service
public class orderService {
    private orderRepository orderRepository;
    private productRepository productRepository;

    public orderService(orderRepository orderRepository, productRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public eOrder getOrderDetail(int orderId) {
        Optional<eOrder> order;
        order = this.orderRepository.findById(orderId);
        return order.isPresent() ? order.get() : null;
    }

    public float getCartAmount(List<shoppingCart> shoppingCartList) {
        float totalCartAmount = 0f;

        for (shoppingCart cart : shoppingCartList) {
            int productId = cart.getProductId();
            Optional<product> product = productRepository.findById(productId);

            if (product.isPresent()) {
                product product1 = product.get();
                int availableQuantity = product1.getQuantity();

                if (availableQuantity < cart.getQuantity()) {
                    cart.setQuantity(availableQuantity);
                }

                float singleCartAmount = cart.getQuantity() * product1.getPrice();
                totalCartAmount += singleCartAmount;

                product1.setQuantity(availableQuantity - cart.getQuantity());
                cart.setProductName(product1.getName());
                cart.setAmount(singleCartAmount);

                productRepository.save(product1);
            }
        }

        return totalCartAmount;
    }




    public eOrder saveOrder(eOrder order) {
        return orderRepository.save(order);
    }
}
