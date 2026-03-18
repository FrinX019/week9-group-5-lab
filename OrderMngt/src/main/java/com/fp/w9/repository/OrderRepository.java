package com.fp.w9.repository;

import com.fp.w9.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByDate(LocalDate date);

    List<Order> findByStockQuote(String stockQuote);

    List<Order> findByOrderType(String orderType);
}
