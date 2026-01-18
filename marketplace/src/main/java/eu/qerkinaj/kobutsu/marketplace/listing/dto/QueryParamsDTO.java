package eu.qerkinaj.kobutsu.marketplace.listing.dto;

import eu.qerkinaj.kobutsu.marketplace.currency.SupportedCurrency;
import eu.qerkinaj.kobutsu.marketplace.listing.enums.Condition;

public record QueryParamsDTO(
        Condition condition,
        SupportedCurrency currency,
        String category,
        int page,
        int size
) {
}
