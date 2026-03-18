package com.fp.w9.repository;

import com.fp.w9.model.OrderTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Order database operations.
 * Supports storing and retrieving OrderTransaction records.
 */
@Repository
public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, Long> {

    Optional<OrderTransaction> findByOrderId(String orderId);

    List<OrderTransaction> findByAccountId(String accountId);

    List<OrderTransaction> findByOrderIdOrderByDateDesc(String orderId);
}
