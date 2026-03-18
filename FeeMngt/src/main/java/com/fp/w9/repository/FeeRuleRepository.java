package com.fp.w9.repository;

import com.fp.w9.entity.FeeRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeeRuleRepository extends JpaRepository<FeeRule, Long> {

    Optional<FeeRule> findByOrderType(String orderType);
}
