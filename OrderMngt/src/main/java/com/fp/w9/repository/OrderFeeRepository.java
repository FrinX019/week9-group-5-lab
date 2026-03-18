package com.fp.w9.repository;

import com.fp.w9.entity.OrderFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderFeeRepository extends JpaRepository<OrderFee, Long> {

    List<OrderFee> findByOrderOrderId(Long orderId);
}
