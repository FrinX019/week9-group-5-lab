package com.fp.w9.repository;

import com.fp.w9.entity.MarketTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketTransactionRepository extends JpaRepository<MarketTransaction, Long> {

    List<MarketTransaction> findByOrderOrderId(Long orderId);
}
