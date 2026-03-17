package com.fp.w9.repository;

import com.fp.w9.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {

    List<AccountTransaction> findByOrderOrderId(Long orderId);
}
