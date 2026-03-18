package com.fp.w9.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "market_order")
public class MarketOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(nullable = false, length = 20)
    private String stockQuote;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @Column(length = 10)
    private String orderType;

    @Column(length = 20)
    private String status;

    @Column(name = "placed_at")
    private LocalDateTime placedAt;

    public MarketOrder() {
        this.placedAt = LocalDateTime.now();
        this.status = "PLACED";
    }

    public MarketOrder(String orderId, String stockQuote, Integer quantity, BigDecimal price, String orderType) {
        this();
        this.orderId = orderId;
        this.stockQuote = stockQuote;
        this.quantity = quantity;
        this.price = price;
        this.orderType = orderType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStockQuote() {
        return stockQuote;
    }

    public void setStockQuote(String stockQuote) {
        this.stockQuote = stockQuote;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(LocalDateTime placedAt) {
        this.placedAt = placedAt;
    }
}
