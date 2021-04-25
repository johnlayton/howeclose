package com.vanguard.trading.service;

import com.vanguard.trading.domain.jpa.Trade;
import com.vanguard.trading.repository.TradeRepository;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradingService {

    private final TradeRepository repository;

    private final Predicate<Trade> sellerFilter;
    private final Predicate<Trade> anagramFilter;

    @Autowired
    public TradingService(TradeRepository repository,
                          Predicate<Trade> sellerFilter,
                          Predicate<Trade> anagramFilter) {
        this.repository = repository;
        this.sellerFilter = sellerFilter;
        this.anagramFilter = anagramFilter;
    }

    public List<Trade> filtered() {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
            .filter(anagramFilter.negate())
            .filter(sellerFilter)
            .collect(Collectors.toList());
    }
}
