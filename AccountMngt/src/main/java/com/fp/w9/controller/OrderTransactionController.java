package com.fp.w9.controller;

import com.fp.w9.model.OrderTransaction;
import com.fp.w9.service.OrderTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for AccountMngt.
 * Exposes API endpoints for OrderTransaction operations.
 * Consumes and serves data via REST API.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderTransactionController {

    private final OrderTransactionService orderTransactionService;

    public OrderTransactionController(OrderTransactionService orderTransactionService) {
        this.orderTransactionService = orderTransactionService;
    }

    /**
     * Store OrderTransaction to database.
     * POST /api/orders
     * Body: { "orderId": "...", "date": "...", "price": ..., "quantity": ..., "accountId": "...", "stockId": "..." }
     */
    @PostMapping
    public ResponseEntity<OrderTransaction> createOrder(@RequestBody Map<String, Object> request) {
        OrderTransaction order = new OrderTransaction();
        order.setOrderId((String) request.get("orderId"));

        Object dateVal = request.get("date");
        if (dateVal instanceof String) {
            order.setDate(LocalDateTime.parse((String) dateVal));
        } else {
            order.setDate(LocalDateTime.now());
        }

        Object priceVal = request.get("price");
        order.setPrice(priceVal instanceof Number
                ? BigDecimal.valueOf(((Number) priceVal).doubleValue())
                : BigDecimal.ZERO);

        Object qtyVal = request.get("quantity");
        order.setQuantity(qtyVal instanceof Number ? ((Number) qtyVal).intValue() : 0);

        if (request.containsKey("accountId")) {
            order.setAccountId((String) request.get("accountId"));
        }
        if (request.containsKey("stockId")) {
            order.setStockId((String) request.get("stockId"));
        }
        if (request.containsKey("orderType")) {
            order.setOrderType(OrderTransaction.OrderType.valueOf((String) request.get("orderType")));
        }

        OrderTransaction saved = orderTransactionService.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Store OrderTransaction via typed DTO-style request.
     * POST /api/orders/store
     */
    @PostMapping("/store")
    public ResponseEntity<OrderTransaction> storeOrderTransaction(@RequestBody OrderTransactionRequest request) {
        OrderTransaction order = new OrderTransaction();
        order.setOrderId(request.getOrderId());
        order.setDate(request.getDate() != null ? request.getDate() : LocalDateTime.now());
        order.setPrice(request.getPrice());
        order.setQuantity(request.getQuantity());
        order.setAccountId(request.getAccountId());
        order.setStockId(request.getStockId());
        if (request.getOrderType() != null) {
            order.setOrderType(OrderTransaction.OrderType.valueOf(request.getOrderType()));
        }

        OrderTransaction saved = orderTransactionService.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderTransaction> getByOrderId(@PathVariable String orderId) {
        return orderTransactionService.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<OrderTransaction> listAll() {
        return orderTransactionService.findAll();
    }

    @GetMapping("/account/{accountId}")
    public List<OrderTransaction> listByAccount(@PathVariable String accountId) {
        return orderTransactionService.findByAccountId(accountId);
    }

    /**
     * Request DTO for storing OrderTransaction (orderId, date, price, quantity).
     */
    public static class OrderTransactionRequest {
        private String orderId;
        private LocalDateTime date;
        private BigDecimal price;
        private Integer quantity;
        private String accountId;
        private String stockId;
        private String orderType;

        public String getOrderId() { return orderId; }
        public void setOrderId(String orderId) { this.orderId = orderId; }
        public LocalDateTime getDate() { return date; }
        public void setDate(LocalDateTime date) { this.date = date; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public String getAccountId() { return accountId; }
        public void setAccountId(String accountId) { this.accountId = accountId; }
        public String getStockId() { return stockId; }
        public void setStockId(String stockId) { this.stockId = stockId; }
        public String getOrderType() { return orderType; }
        public void setOrderType(String orderType) { this.orderType = orderType; }
    }
}
