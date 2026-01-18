package eu.qerkinaj.kobutsu.marketplace.currency.producer;

import eu.qerkinaj.kobutsu.marketplace.currency.SupportedCurrency;
import eu.qerkinaj.kobutsu.marketplace.currency.qualifier.CurrentCurrency;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class CurrencyProducer {

    private static final Logger log = LoggerFactory.getLogger(CurrencyProducer.class);

    @Context
    UriInfo uriInfo;

    // TODO: inject GeoService when ready
    // @Inject GeoService geoService;

    @Produces
    @CurrentCurrency
    public SupportedCurrency getCurrency() {
        String currencyParam = uriInfo.getQueryParameters().getFirst("currency");
        log.trace("entering getCurrency(), raw param='{}'", currencyParam);

        if (currencyParam != null && !currencyParam.isBlank()) {
            String candidate = currencyParam.trim().toUpperCase();
            log.debug("normalized currency param = {}", candidate);
            try {
                SupportedCurrency sc = SupportedCurrency.valueOf(candidate);
                log.info("using client-passed currency: {}", sc);
                log.trace("exiting getCurrency() → {}", sc);
                return sc;
            } catch (IllegalArgumentException iae) {
                log.warn("invalid currency '{}', defaulting to EUR", candidate, iae);
            }
        } else {
            log.debug("no 'currency' query param provided");
        }

        SupportedCurrency fallback = SupportedCurrency.EUR;
        log.trace("exiting getCurrency() → {}", fallback);
        return fallback;
    }
}
