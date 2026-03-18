package com.fp.w9.service;

import com.fp.w9.entity.FeeRule;
import com.fp.w9.repository.FeeRuleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class FeeService {

    private final FeeRuleRepository feeRuleRepository;

    public FeeService(FeeRuleRepository feeRuleRepository) {
        this.feeRuleRepository = feeRuleRepository;
    }

    // Return all fee rules
    public List<FeeRule> findAllRules() {
        return feeRuleRepository.findAll();
    }

    // Calculate fee = amount * ratePercent / 100
    // Defaults to 1% if no rule is found for the given orderType
    public BigDecimal calculateFee(String orderType, BigDecimal amount) {
        BigDecimal rate = feeRuleRepository.findByOrderType(orderType)
                .map(FeeRule::getRatePercent)
                .orElse(BigDecimal.ONE);

        return amount.multiply(rate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
