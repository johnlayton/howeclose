package com.vanguard.trading.repository;

import com.vanguard.trading.domain.jpa.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TradeRepository extends JpaRepository<Trade, String>, JpaSpecificationExecutor<Trade> {
}
