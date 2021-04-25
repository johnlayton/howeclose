package com.vanguard.trading.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Chars;
import com.vanguard.trading.domain.jpa.Trade;
import java.util.function.Predicate;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties({
    TradingProperties.class
})
public class TradingConfiguration {

    @Bean
    @Primary
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    public ObjectMapper xmlMapper() {
        return new XmlMapper()
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    public OpenApiCustomiser openApiCustomiser(TradingProperties properties) {
        return openApi -> {
            openApi.getInfo().setTitle(properties.getTitle());
            openApi.getInfo().setDescription(properties.getDescription());
            openApi.getInfo().setVersion(properties.getVersion());
        };
    }

    @Bean
    public Predicate<Trade> sellerFilter(TradingProperties properties) {
        return properties.getFilters().stream()
            .map(filter -> (Predicate<Trade>) trade -> filter.getName().equals(trade.getSeller()) && filter.getCurrency().equals(trade.getCurrency()))
            .reduce(trade -> false, Predicate::or);
    }

    @Bean
    public Predicate<Trade> anagramFilter() {
        return trade -> trade.getSeller().length() == trade.getBuyer().length()
            && toMultiSet(trade.getSeller()).equals(toMultiSet(trade.getBuyer()));
    }

    private static Multiset<Character> toMultiSet(String input) {
        return HashMultiset.create(Chars.asList(input.toCharArray()));
    }
}
