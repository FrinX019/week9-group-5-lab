package com.fp.w9.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MarketTransactionDTO {

    private Long id;
    private Long orderId;
    private String marketId;
    private BigDecimal price;
    private Integer quantity;
    private String side;
    private LocalDateTime executionTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
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
