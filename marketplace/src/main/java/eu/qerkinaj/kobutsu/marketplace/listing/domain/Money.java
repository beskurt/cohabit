package eu.qerkinaj.kobutsu.marketplace.listing.domain;

import eu.qerkinaj.kobutsu.marketplace.currency.SupportedCurrency;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

@Embeddable
public class Money {

    @Column(name = "price", precision = 19, scale = 4, nullable = false)
    public BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", length = 3, nullable = false)
    public SupportedCurrency currency;
}
