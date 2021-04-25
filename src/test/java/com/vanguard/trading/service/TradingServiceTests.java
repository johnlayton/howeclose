package com.vanguard.trading.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

import com.vanguard.trading.domain.jpa.Trade;
import com.vanguard.trading.repository.TradeRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TradingServiceTests {

    @Mock
    private TradeRepository repository;

    private final Trade expected = new Trade("Buyer", "Seller", BigDecimal.valueOf(10000, 2), "AUD");

    @BeforeEach
    public void before() {
        when(repository.findAll())
            .thenReturn(List.of(expected));
    }

    @Test
    public void shouldReturnValidTradeWhenAnagramFalseAndSellerTrue() {
        assertThat(new TradingService(repository, trade -> true, trade -> false).filtered())
            .contains(expected);
    }

    @Test
    public void shouldNotReturnInValidTradeWhenAnagramTrueAndSellerTrue() {
        assertThat(new TradingService(repository, trade -> true, trade -> true).filtered())
            .isEmpty();
    }

    @Test
    public void shouldNotReturnInValidTradeWhenAnagramFalseButAndSellerIncorrect() {
        assertThat(new TradingService(repository, trade -> false, trade -> false).filtered())
            .isEmpty();
    }

}
