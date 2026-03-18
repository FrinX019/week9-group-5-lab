package com.fp.w9.controller;

import com.fp.w9.entity.FeeRule;
import com.fp.w9.service.FeeService;
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
 * REST Controller for Fee Management Service: 8082
 */
@RestController
@RequestMapping("/api/fees")
public class FeeController {

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    // list all fee rules
    // GET http://localhost:8082/api/fees/rules
    @GetMapping("/rules")
    public List<FeeRule> getAllRules() {
        return feeService.findAllRules();
    }

    // caculate fee from order
    // Body: { "orderType": "BUY", "amount": 1000.00 }
    @PostMapping("/calculate")
    public ResponseEntity<Map<String, Object>> calculateFee(@RequestBody Map<String, Object> request) {
        String orderType = (String) request.get("orderType");
        Object amountVal = request.get("amount");

        BigDecimal amount = amountVal instanceof Number
                ? BigDecimal.valueOf(((Number) amountVal).doubleValue())
                : BigDecimal.ZERO;

        BigDecimal fee = feeService.calculateFee(orderType, amount);

        Map<String, Object> response = Map.of(
                "orderType", orderType,
                "amount", amount,
                "fee", fee);

        return ResponseEntity.ok(response);
    }
}
