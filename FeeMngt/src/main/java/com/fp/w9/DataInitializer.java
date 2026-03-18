package com.fp.w9;

import com.fp.w9.entity.FeeRule;
import com.fp.w9.repository.FeeRuleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FeeRuleRepository feeRuleRepository;

    public DataInitializer(FeeRuleRepository feeRuleRepository) {
        this.feeRuleRepository = feeRuleRepository;
    }

    @Override
    public void run(String... args) {
        if (feeRuleRepository.count() == 0) {
            feeRuleRepository.saveAll(List.of(
                    new FeeRule("BUY", new BigDecimal("1.50")),
                    new FeeRule("SELL", new BigDecimal("1.00"))));
            System.out.println("Fee rules seeded: BUY=1.5%, SELL=1.0%");
        }
    }
}
