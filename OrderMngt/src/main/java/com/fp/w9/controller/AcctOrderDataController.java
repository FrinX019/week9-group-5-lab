package com.fp.w9.controller;

import com.fp.w9.dto.AccountTransactionDTO;
import com.fp.w9.dto.FeesPerOrderDTO;
import com.fp.w9.dto.MarketTransactionDTO;
import com.fp.w9.entity.*;
import com.fp.w9.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @RestController - Read/Select APIs for fees, account transactions, market transactions per order.
 */
@RestController
@RequestMapping("/api")
public class AcctOrderDataController {

    private final OrderRepository orderRepository;
    private final OrderFeeRepository orderFeeRepository;
    private final AccountTransactionRepository accountTransactionRepository;
    private final MarketTransactionRepository marketTransactionRepository;

    public AcctOrderDataController(OrderRepository orderRepository,
                                   OrderFeeRepository orderFeeRepository,
                                   AccountTransactionRepository accountTransactionRepository,
                                   MarketTransactionRepository marketTransactionRepository) {
        this.orderRepository = orderRepository;
        this.orderFeeRepository = orderFeeRepository;
        this.accountTransactionRepository = accountTransactionRepository;
        this.marketTransactionRepository = marketTransactionRepository;
    }

    /** GET /api/orders/{orderId}/fees - Fees per order */
    @GetMapping("/orders/{orderId}/fees")
    public ResponseEntity<FeesPerOrderDTO> getFeesPerOrder(@PathVariable Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    List<OrderFee> fees = orderFeeRepository.findByOrderOrderId(orderId);
                    FeesPerOrderDTO dto = new FeesPerOrderDTO();
                    dto.setOrderId(orderId);
                    dto.setOrderRef(order.getStockQuote() + " x " + order.getQuantity());
                    dto.setFees(fees.stream()
                            .map(f -> new FeesPerOrderDTO.FeeItem(f.getFeeType(), f.getFeeAmount()))
                            .collect(Collectors.toList()));
                    dto.setTotalFee(fees.stream()
                            .map(OrderFee::getFeeAmount)
                            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /** GET /api/orders/{orderId}/account-transactions - Account transactions per order */
    @GetMapping("/orders/{orderId}/account-transactions")
    public ResponseEntity<List<AccountTransactionDTO>> getAccountTransactionsPerOrder(@PathVariable Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            return ResponseEntity.notFound().build();
        }
        List<AccountTransaction> txns = accountTransactionRepository.findByOrderOrderId(orderId);
        List<AccountTransactionDTO> dtos = txns.stream()
                .map(this::toAccountTransactionDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /** GET /api/orders/{orderId}/market-transactions - Market transactions per order */
    @GetMapping("/orders/{orderId}/market-transactions")
    public ResponseEntity<List<MarketTransactionDTO>> getMarketTransactionsPerOrder(@PathVariable Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            return ResponseEntity.notFound().build();
        }
        List<MarketTransaction> txns = marketTransactionRepository.findByOrderOrderId(orderId);
        List<MarketTransactionDTO> dtos = txns.stream()
                .map(this::toMarketTransactionDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /** GET /api/market-transactions - All market transactions */
    @GetMapping("/market-transactions")
    public List<MarketTransactionDTO> getAllMarketTransactions() {
        return marketTransactionRepository.findAll().stream()
                .map(this::toMarketTransactionDTO)
                .collect(Collectors.toList());
    }

    private AccountTransactionDTO toAccountTransactionDTO(AccountTransaction t) {
        AccountTransactionDTO dto = new AccountTransactionDTO();
        dto.setId(t.getId());
        dto.setOrderId(t.getOrder().getOrderId());
        dto.setAccountId(t.getAccountId());
        dto.setAmount(t.getAmount());
        dto.setTransactionType(t.getTransactionType());
        dto.setDescription(t.getDescription());
        dto.setTransactionDate(t.getTransactionDate());
        return dto;
    }

    private MarketTransactionDTO toMarketTransactionDTO(MarketTransaction t) {
        MarketTransactionDTO dto = new MarketTransactionDTO();
        dto.setId(t.getId());
        dto.setOrderId(t.getOrder().getOrderId());
        dto.setMarketId(t.getMarketId());
        dto.setPrice(t.getPrice());
        dto.setQuantity(t.getQuantity());
        dto.setSide(t.getSide());
        dto.setExecutionTime(t.getExecutionTime());
        return dto;
    }
}
