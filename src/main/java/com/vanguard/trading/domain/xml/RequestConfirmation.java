package com.vanguard.trading.domain.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JacksonXmlRootElement(localName = "requestConfirmation")
@Valid
public class RequestConfirmation {

    public static class Trade {

        public static class VarianceOptionTransactionSupplement {

            public static class PartyReference {

                @JacksonXmlProperty(localName = "href", isAttribute = true)
                @NotNull
                @Valid
                private String href;

                public String getHref() {
                    return href;
                }
            }

            public static class EquityPremium {

                public static class PaymentAmount {

                    @JacksonXmlProperty(localName = "currency")
                    @NotNull
                    @Valid
                    private String currency;

                    @JacksonXmlProperty(localName = "amount")
                    @NotNull
                    @Valid
                    private BigDecimal amount;

                    public String getCurrency() {
                        return currency;
                    }

                    public BigDecimal getAmount() {
                        return amount;
                    }
                }

                @JacksonXmlProperty(localName = "paymentAmount")
                @NotNull
                @Valid
                private PaymentAmount paymentAmount;

                public PaymentAmount getPaymentAmount() {
                    return paymentAmount;
                }
            }

            @JacksonXmlProperty(localName = "buyerPartyReference")
            @NotNull
            @Valid
            private PartyReference buyer;
            @JacksonXmlProperty(localName = "sellerPartyReference")
            @NotNull
            @Valid
            private PartyReference seller;
            @JacksonXmlProperty(localName = "equityPremium")
            @NotNull
            @Valid
            private EquityPremium equity;

            public PartyReference getBuyer() {
                return buyer;
            }

            public PartyReference getSeller() {
                return seller;
            }

            public EquityPremium getEquity() {
                return equity;
            }
        }

        @JacksonXmlProperty(localName = "varianceOptionTransactionSupplement")
        @NotNull
        @Valid
        private VarianceOptionTransactionSupplement option;

        public VarianceOptionTransactionSupplement getOption() {
            return option;
        }
    }

    @JacksonXmlProperty(localName = "trade")
    @NotNull
    @Valid
    private Trade trade;

    public Trade getTrade() {
        return trade;
    }
}
