package com.vanguard.trading.repository;

import com.vanguard.trading.domain.jpa.Trade;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface TradeRepository extends CrudRepository<Trade, UUID> {
}
