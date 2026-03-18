package com.fp.w9.controller;

import com.fp.w9.model.OrderTransaction;
import com.fp.w9.service.OrderTransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

/**
 * Controller for Thymeleaf view pages.
 */
@Controller
public class OrderViewController {

    private final OrderTransactionService orderTransactionService;

    public OrderViewController(OrderTransactionService orderTransactionService) {
        this.orderTransactionService = orderTransactionService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderTransactionService.findAll());
        return "orders";
    }

    @GetMapping("/orders/new")
    public String newOrderForm() {
        return "order-form";
    }

    @PostMapping("/orders/create")
    public String createOrder(
            @RequestParam String orderId,
            @RequestParam BigDecimal price,
            @RequestParam Integer quantity,
            @RequestParam(required = false) String accountId,
            @RequestParam(required = false) String stockId,
            @RequestParam(defaultValue = "SELL") String orderType,
            RedirectAttributes redirectAttributes) {
        OrderTransaction order = new OrderTransaction();
        order.setOrderId(orderId);
        order.setDate(java.time.LocalDateTime.now());
        order.setPrice(price);
        order.setQuantity(quantity);
        order.setAccountId(accountId);
        order.setStockId(stockId);
        order.setOrderType(OrderTransaction.OrderType.valueOf(orderType));

        orderTransactionService.save(order);
        redirectAttributes.addFlashAttribute("message", "Order created successfully.");
        return "redirect:/orders";
    }
}
