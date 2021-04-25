package com.vanguard.trading.confg;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.vanguard.trading.config.TradingConfiguration;
import com.vanguard.trading.config.TradingProperties;
import com.vanguard.trading.config.TradingProperties.Filter;
import com.vanguard.trading.domain.jpa.Trade;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

public class TradingConfigurationTests {

    @Test
    public void shouldCreateAnagramFilterWhichTestsPositiveForPartyNameAnagrams() {
        final Predicate<Trade> anagramFilter = new TradingConfiguration().anagramFilter();

        assertThat(anagramFilter.test(new Trade(
            "BUYER", "UYREB", new BigDecimal(100L), "AUD"
        ))).isTrue();
        assertThat(anagramFilter.test(new Trade(
            "BUY_ER", "UYREB_", new BigDecimal(100L), "AUD"
        ))).isTrue();
        assertThat(anagramFilter.test(new Trade(
            "A1", "1A", new BigDecimal(100L), "AUD"
        ))).isTrue();
    }

    @Test
    public void shouldCreateAnagramFilterWhichTestsNegativeForPartyNameAnagrams() {
        final Predicate<Trade> anagramFilter = new TradingConfiguration().anagramFilter();

        assertThat(anagramFilter.test(new Trade(
            "BUYER", "UYRE", new BigDecimal(100L), "AUD"
        ))).isFalse();
        assertThat(anagramFilter.test(new Trade(
            "BUY_ER", "BUYER", new BigDecimal(100L), "AUD"
        ))).isFalse();
        assertThat(anagramFilter.test(new Trade(
            "A1", "1a", new BigDecimal(100L), "AUD"
        ))).isFalse();
    }

    @Test
    public void shouldCreateSellerFilterWhichTestsPositiveForMatchingSellerNameAndCurrency() {
        final TradingProperties properties = createTradingProperties(createFilter("SELLER", "AUD"));
        final Predicate<Trade> filter = new TradingConfiguration().sellerFilter(properties);

        assertThat(filter.test(new Trade(
            "BUYER", "SELLER", new BigDecimal(100L), "AUD"
        ))).isTrue();
    }

    @Test
    public void shouldCreateSellerFilterWhichTestsNegativeForNonMatchingSellerNameAndCurrency() {
        final TradingProperties properties = createTradingProperties(createFilter("SELLER", "USD"));
        final Predicate<Trade> filter = new TradingConfiguration().sellerFilter(properties);

        assertThat(filter.test(new Trade(
            "BUYER", "SELLER", new BigDecimal(100L), "AUD"
        ))).isFalse();
        assertThat(filter.test(new Trade(
            "SELLER", "BUYER", new BigDecimal(100L), "AUD"
        ))).isFalse();
        assertThat(filter.test(new Trade(
            "ME", "UYRE", new BigDecimal(100L), "USD"
        ))).isFalse();
    }

    private TradingProperties createTradingProperties(Filter...  filter) {
        final TradingProperties properties = new TradingProperties();
        properties.setFilters(List.of(filter));
        return properties;
    }

    private Filter createFilter(String name, String currency) {
        final Filter filter = new Filter();
        filter.setName(name);
        filter.setCurrency(currency);
        return filter;
    }
}
