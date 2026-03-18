package com.fp.w9.controller;

import com.fp.w9.entity.Order;
import com.fp.w9.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        List<Order> orders = orderService.findAllOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("order", new Order());
        return "orders";
    }

    @PostMapping("/orders/save")
    public String saveOrder(@ModelAttribute Order order) {
        if (order.getDate() == null) order.setDate(LocalDate.now());
        if (order.getStatus() == null) order.setStatus("PENDING");
        if (order.getOrderType() == null) order.setOrderType("BUY");
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

}
