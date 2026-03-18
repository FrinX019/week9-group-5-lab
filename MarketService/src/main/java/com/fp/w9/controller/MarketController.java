package com.fp.w9.controller;

import com.fp.w9.entity.MarketOrder;
import com.fp.w9.service.MarketOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * REST Controller : 8083
 */
@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final MarketOrderService marketOrderService;

    public MarketController(MarketOrderService marketOrderService) {
        this.marketOrderService = marketOrderService;
    }

    // place order
    // POST http://localhost:8083/api/market/place
    // Body: { "orderId": "ORD-001", "stockQuote": "AAPL", "quantity": 100, "price":
    // 150.00, "orderType": "BUY" }
    @PostMapping("/place")
    public ResponseEntity<MarketOrder> placeOrder(@RequestBody Map<String, Object> request) {
        MarketOrder order = new MarketOrder();
        order.setOrderId((String) request.get("orderId"));
        order.setStockQuote((String) request.get("stockQuote"));

        Object qtyVal = request.get("quantity");
        order.setQuantity(qtyVal instanceof Number ? ((Number) qtyVal).intValue() : 0);

        Object priceVal = request.get("price");
        order.setPrice(priceVal instanceof Number
                ? BigDecimal.valueOf(((Number) priceVal).doubleValue())
                : BigDecimal.ZERO);

        if (request.containsKey("orderType")) {
            order.setOrderType((String) request.get("orderType"));
        }

        MarketOrder saved = marketOrderService.placeOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // list orders
    @GetMapping("/orders")
    public List<MarketOrder> listAllOrders() {
        return marketOrderService.findAll();
    }
}
