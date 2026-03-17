package com.fp.w9.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "market_transactions")
public class MarketTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, length = 50)
    private String marketId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 20)
    private String side;

    private LocalDateTime executionTime;

    public MarketTransaction() {
        this.executionTime = LocalDateTime.now();
    }

    public MarketTransaction(Order order, String marketId, BigDecimal price, Integer quantity, String side) {
        this();
        this.order = order;
        this.marketId = marketId;
        this.price = price;
        this.quantity = quantity;
        this.side = side;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public String getMarketId() { return marketId; }
    public void setMarketId(String marketId) { this.marketId = marketId; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getSide() { return side; }
    public void setSide(String side) { this.side = side; }
    public LocalDateTime getExecutionTime() { return executionTime; }
    public void setExecutionTime(LocalDateTime executionTime) { this.executionTime = executionTime; }
}
