package com.fp.w9.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, length = 50)
    private String stockQuote;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 20)
    private String orderType; // BUY, SELL

    @Column(length = 20)
    private String status; // PENDING, EXECUTED, CANCELLED

    private LocalDateTime createdAt;

    public Order() {
        this.createdAt = LocalDateTime.now();
    }

    public Order(LocalDate date, String stockQuote, BigDecimal price, Integer quantity) {
        this();
        this.date = date;
        this.stockQuote = stockQuote;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStockQuote() {
        return stockQuote;
    }

    public void setStockQuote(String stockQuote) {
        this.stockQuote = stockQuote;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
