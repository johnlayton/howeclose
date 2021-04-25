package com.vanguard.trading.service;

import com.vanguard.trading.domain.jpa.Trade;
import com.vanguard.trading.domain.xml.RequestConfirmation;
import com.vanguard.trading.domain.xml.RequestConfirmation.Trade.VarianceOptionTransactionSupplement;
import com.vanguard.trading.repository.TradeRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ConfirmationService {

    private final TradeRepository repository;

    @Autowired
    public ConfirmationService(TradeRepository repository) {
        this.repository = repository;
    }

    public void save(@Valid RequestConfirmation confirmation) {
        final VarianceOptionTransactionSupplement option = confirmation.getTrade().getOption();
        repository.save(new Trade(
            option.getBuyer().getHref(),
            option.getSeller().getHref(),
            option.getEquity().getPaymentAmount().getAmount(),
            option.getEquity().getPaymentAmount().getCurrency()
        ));
    }
}
