package com.fp.w9.service;

import com.fp.w9.entity.AccountTransaction;
import com.fp.w9.entity.MarketTransaction;
import com.fp.w9.entity.Order;
import com.fp.w9.entity.OrderFee;
import com.fp.w9.repository.AccountTransactionRepository;
import com.fp.w9.repository.MarketTransactionRepository;
import com.fp.w9.repository.OrderFeeRepository;
import com.fp.w9.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AccountTransactionRepository accountTransactionRepository;
    private final OrderFeeRepository orderFeeRepository;
    private final MarketTransactionRepository marketTransactionRepository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository,
                        AccountTransactionRepository accountTransactionRepository,
                        OrderFeeRepository orderFeeRepository,
                        MarketTransactionRepository marketTransactionRepository) {
        this.orderRepository = orderRepository;
        this.accountTransactionRepository = accountTransactionRepository;
        this.orderFeeRepository = orderFeeRepository;
        this.marketTransactionRepository = marketTransactionRepository;
        this.restTemplate = new RestTemplate();
    }

    @Transactional
    public Order saveOrder(Order order) {
        // 1. Record order details in database
        order = orderRepository.save(order);

        try {
            BigDecimal totalAmount = order.getPrice().multiply(new BigDecimal(order.getQuantity()));

            // 2. Account transactions service (Port 8081)
            Map<String, Object> accReq = new HashMap<>();
            accReq.put("orderId", String.valueOf(order.getOrderId()));
            accReq.put("date", order.getDate() != null ? order.getDate().toString() + "T00:00:00" : null);
            accReq.put("price", order.getPrice());
            accReq.put("quantity", order.getQuantity());
            accReq.put("accountId", "ABC"); // As per diagram: account ABC
            accReq.put("stockId", order.getStockQuote());
            accReq.put("orderType", order.getOrderType() != null ? order.getOrderType().toUpperCase() : "BUY");
            
            try {
                restTemplate.postForObject("http://localhost:8081/api/orders", accReq, Map.class);
            } catch (Exception e) {
                System.err.println("Failed to contact AccountMngt: " + e.getMessage());
            }

            // Save locally
            AccountTransaction at = new AccountTransaction(order, "ABC", totalAmount, order.getOrderType());
            at.setDescription("Reservation for order " + order.getOrderId());
            accountTransactionRepository.save(at);

            // 3. Fees service (Port 8082)
            Map<String, Object> feeReq = new HashMap<>();
            feeReq.put("orderType", order.getOrderType());
            feeReq.put("amount", totalAmount);
            
            BigDecimal feeAmt = BigDecimal.ZERO;
            try {
                Map feeResp = restTemplate.postForObject("http://localhost:8082/api/fees/calculate", feeReq, Map.class);
                if (feeResp != null && feeResp.get("fee") != null) {
                    feeAmt = new BigDecimal(feeResp.get("fee").toString());
                }
            } catch (Exception e) {
                System.err.println("Failed to contact FeeMngt: " + e.getMessage());
            }

            // Save locally
            OrderFee fee = new OrderFee(order, "Processing Fee", feeAmt);
            orderFeeRepository.save(fee);

            // 4. Market service (Port 8083)
            Map<String, Object> marketReq = new HashMap<>();
            marketReq.put("orderId", String.valueOf(order.getOrderId()));
            marketReq.put("stockQuote", order.getStockQuote());
            marketReq.put("quantity", order.getQuantity());
            marketReq.put("price", order.getPrice());
            marketReq.put("orderType", order.getOrderType());
            
            try {
                restTemplate.postForObject("http://localhost:8083/api/market/place", marketReq, Map.class);
            } catch (Exception e) {
                System.err.println("Failed to contact MarketService: " + e.getMessage());
            }

            // Save locally
            MarketTransaction mt = new MarketTransaction(order, "STOCK_EXCHANGE", order.getPrice(), order.getQuantity(), order.getOrderType());
            marketTransactionRepository.save(mt);

            order.setStatus("EXECUTED");
            orderRepository.save(order);

        } catch (Exception e) {
            e.printStackTrace();
            order.setStatus("ERROR");
            orderRepository.save(order);
        }

        return order;
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
