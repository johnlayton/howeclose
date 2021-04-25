package com.vanguard.trading.domain.jpa;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String buyer;
    private String seller;
    private BigDecimal amount;
    private String currency;

    public Trade() {
    }

    public Trade(String buyer, String seller, BigDecimal amount, String currency) {
        this.buyer = buyer;
        this.seller = seller;
        this.amount = amount;
        this.currency = currency;
    }

    public UUID getId() {
        return id;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getSeller() {
        return seller;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
