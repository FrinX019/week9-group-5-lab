package com.fp.w9.repository;

import com.fp.w9.entity.MarketOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketOrderRepository extends JpaRepository<MarketOrder, Long> {

    List<MarketOrder> findByStockQuote(String stockQuote);
}
