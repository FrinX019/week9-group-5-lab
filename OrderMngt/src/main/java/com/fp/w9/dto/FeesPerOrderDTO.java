package com.fp.w9.dto;

import java.math.BigDecimal;
import java.util.List;

public class FeesPerOrderDTO {

    private Long orderId;
    private String orderRef;
    private List<FeeItem> fees;
    private BigDecimal totalFee;

    public static class FeeItem {
        private String feeType;
        private BigDecimal feeAmount;

        public FeeItem() {}
        public FeeItem(String feeType, BigDecimal feeAmount) {
            this.feeType = feeType;
            this.feeAmount = feeAmount;
        }
        public String getFeeType() { return feeType; }
        public void setFeeType(String feeType) { this.feeType = feeType; }
        public BigDecimal getFeeAmount() { return feeAmount; }
        public void setFeeAmount(BigDecimal feeAmount) { this.feeAmount = feeAmount; }
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getOrderRef() { return orderRef; }
    public void setOrderRef(String orderRef) { this.orderRef = orderRef; }
    public List<FeeItem> getFees() { return fees; }
    public void setFees(List<FeeItem> fees) { this.fees = fees; }
    public BigDecimal getTotalFee() { return totalFee; }
    public void setTotalFee(BigDecimal totalFee) { this.totalFee = totalFee; }
}
