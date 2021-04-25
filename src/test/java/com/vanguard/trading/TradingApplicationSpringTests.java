package com.vanguard.trading;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.vanguard.trading.domain.jpa.Trade;
import com.vanguard.trading.domain.xml.RequestConfirmation;
import com.vanguard.trading.service.ConfirmationService;
import java.math.BigDecimal;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class TradingApplicationSpringTests {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ConfirmationService service;

    @Value("http://localhost:${local.server.port}/trades")
    private String appPath;

    @Test
    public void filtersTrades() {
        final Trade[] trades = template.getForObject(appPath, Trade[].class);

        assertThat(trades.length).isEqualTo(4);

        assertThat(trades[0].getAmount()).isEqualTo(BigDecimal.valueOf(10000, 2));
        assertThat(trades[0].getBuyer()).isEqualTo("LEFT_BANK");
        assertThat(trades[0].getSeller()).isEqualTo("EMU_BANK");
        assertThat(trades[1].getAmount()).isEqualTo(BigDecimal.valueOf(20000, 2));
        assertThat(trades[2].getAmount()).isEqualTo(BigDecimal.valueOf(50000, 2));
        assertThat(trades[3].getAmount()).isEqualTo(BigDecimal.valueOf(60000, 2));
    }

    @Test
    public void shouldValidateRequestConfirmationOnSave() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            service.save(new RequestConfirmation());
        });
    }
}
