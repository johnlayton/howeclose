package com.vanguard.trading.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

import com.vanguard.trading.domain.api.TradeFilter;
import com.vanguard.trading.domain.jpa.Trade;
import com.vanguard.trading.service.TradingService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trades")
public class TradingController {

    private final TradingService service;

    public TradingController(TradingService service) {
        this.service = service;
    }

    @GetMapping(produces = {
        APPLICATION_JSON_VALUE,
        APPLICATION_XML_VALUE
    })
    @Operation
    public List<Trade> list(TradeFilter filter) {
        return service.filtered(filter);
    }

}
