package com.fp.w9.service;

import com.fp.w9.model.OrderTransaction;
import com.fp.w9.repository.OrderTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Order operations.
 * Orchestrates business logic for storing and retrieving OrderTransaction records.
 */
@Service
public class OrderTransactionService {

    private final OrderTransactionRepository orderTransactionRepository;

    public OrderTransactionService(OrderTransactionRepository orderTransactionRepository) {
        this.orderTransactionRepository = orderTransactionRepository;
    }

    @Transactional
    public OrderTransaction save(OrderTransaction orderTransaction) {
        return orderTransactionRepository.save(orderTransaction);
    }

    public Optional<OrderTransaction> findByOrderId(String orderId) {
        return orderTransactionRepository.findByOrderId(orderId);
    }

    public Optional<OrderTransaction> findById(Long id) {
        return orderTransactionRepository.findById(id);
    }

    public List<OrderTransaction> findAll() {
        return orderTransactionRepository.findAll();
    }

    public List<OrderTransaction> findByAccountId(String accountId) {
        return orderTransactionRepository.findByAccountId(accountId);
    }
}
