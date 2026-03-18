package com.fp.w9.service;

import com.fp.w9.entity.MarketOrder;
import com.fp.w9.repository.MarketOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for market order operations.
 * Handles placing orders to the market and retrieving existing orders.
 */
@Service
public class MarketOrderService {

    private final MarketOrderRepository marketOrderRepository;

    public MarketOrderService(MarketOrderRepository marketOrderRepository) {
        this.marketOrderRepository = marketOrderRepository;
    }

    // Save a new market order (simulates placing to stock exchange)
    @Transactional
    public MarketOrder placeOrder(MarketOrder order) {
        System.out.println("Placing order to stock exchange: " + order.getOrderId()
                + " | " + order.getStockQuote()
                + " | " + order.getOrderType()
                + " | qty=" + order.getQuantity());
        return marketOrderRepository.save(order);
    }

    // Return all market orders
    public List<MarketOrder> findAll() {
        return marketOrderRepository.findAll();
    }
}
