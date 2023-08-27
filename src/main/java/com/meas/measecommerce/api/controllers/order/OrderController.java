package com.meas.measecommerce.api.controllers.order;

import com.meas.measecommerce.models.User;
import com.meas.measecommerce.models.WebOrder;
import com.meas.measecommerce.services.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint to get all orders for a specific user.
     * @param user The user provided by spring security context.
     * @return The list of orders the user had made.
     */
    @GetMapping
    public List<WebOrder> getOrders(@AuthenticationPrincipal User user){
        return orderService.getOrders(user);
    }
}
