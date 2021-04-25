package com.vanguard.trading.domain.api;

import com.vanguard.trading.domain.jpa.Trade;
import java.math.BigDecimal;
import org.springframework.data.domain.Example;

public class TradeFilter {

    private String buyer;
    private String seller;
    private BigDecimal amount;
    private String currency;

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Example<Trade> toExample() {
        return Example.of(new Trade(buyer, seller, amount, currency));
    }
}
