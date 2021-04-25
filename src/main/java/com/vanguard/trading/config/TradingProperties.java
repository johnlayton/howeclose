package com.vanguard.trading.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.info.BuildProperties;

@ConfigurationProperties(prefix = "trading")
public class TradingProperties {

    @Autowired
    private BuildProperties buildProperties;

    private String title;
    private String description;
    private List<Filter> filters;

    public static class Filter {
        private String name;
        private String currency;

        public String getName() {
            return name;
        }

        public String getCurrency() {
            return currency;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }

    public String getVersion() {
        return buildProperties.getVersion();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
}
