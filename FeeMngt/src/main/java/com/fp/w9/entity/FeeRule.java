package com.fp.w9.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "fee_rule")
public class FeeRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // BUY or SELL
    @Column(nullable = false, length = 10)
    private String orderType;

    // Fee percentage, e.g. 1.5 means 1.5%
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal ratePercent;

    public FeeRule() {
    }

    public FeeRule(String orderType, BigDecimal ratePercent) {
        this.orderType = orderType;
        this.ratePercent = ratePercent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getRatePercent() {
        return ratePercent;
    }

    public void setRatePercent(BigDecimal ratePercent) {
        this.ratePercent = ratePercent;
    }
}
