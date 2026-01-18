package eu.qerkinaj.kobutsu.marketplace.currency.ecb;

import com.fasterxml.jackson.databind.JsonNode;
import eu.qerkinaj.kobutsu.marketplace.currency.MoneyDTO;
import eu.qerkinaj.kobutsu.marketplace.currency.SupportedCurrency;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.ListingDTO;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class EcbDataService {

    private static final Logger log = LoggerFactory.getLogger(EcbDataService.class);

    private static final String FREQ = "D";
    private static final String EXR_TYPE = "SP00";
    private static final String EXR_SUFFIX = "A";
    private static final String FORMAT = "jsondata";

    @Inject
    @RestClient
    EcbDataClient ecbDataClient;

    @ConfigProperty(name = "feature.ecb.client")
    boolean enable;

    public List<ListingDTO> convertCurrency(List<ListingDTO> listing, SupportedCurrency currency) {
        log.debug("converting to currency {}", currency.getIsoCode());
        listing.forEach(dto ->
                dto.setPrice(convertCurrency(dto.getPrice(), currency))
        );
        return listing;
    }

    public MoneyDTO convertCurrency(MoneyDTO moneyDTO, SupportedCurrency currency) {
        if (!moneyDTO.currency().equals(currency)) {
            double rate = getLatestRate(moneyDTO.currency(), currency);
            log.debug("got for currency {} rate {}", currency.getIsoCode(), rate);
            return new MoneyDTO(moneyDTO.amount().multiply(BigDecimal.valueOf(rate)), currency);
        }
        return moneyDTO;
    }

    @CacheResult(cacheName = "ecb-currency-rate")
    public double getLatestRate(SupportedCurrency from, SupportedCurrency to) {
        String seriesKey = buildSeriesKey(from, to);
        log.debug("seriesKey {}", seriesKey);
        log.debug("ecbRestClient: {}", enable);
        if (enable) {
            log.info("Getting currency rate from {} to {}", from, to);

            JsonNode root = ecbDataClient.getExchangeData(seriesKey, 1, FORMAT);
            JsonNode seriesNode = root.path("dataSets").get(0).path("series");
            Iterator<String> it = seriesNode.fieldNames();

            if (!it.hasNext()) {
                log.warn("No data found for series {}", seriesKey);
                throw new NoExchangeRateException(from, to);
            }

            String dynKey = it.next();
            JsonNode observationsNode = seriesNode.path(dynKey).path("observations").path("0");
            JsonNode obsArray = observationsNode.get(0);
            log.debug("ObsArray {}", obsArray);
            if (obsArray == null) {
                log.warn("No data found for dyn {}", dynKey);
                throw new NoExchangeRateException(
                        String.format("Empty observations for %s → %s on series %s",
                                from, to, dynKey));
            }
            return obsArray.asDouble();
        }

        log.info("ecbRestClient deactivated");
        return 0.0;
    }

    //https://data-api.ecb.europa.eu/service/data/EXR/D.USD.EUR.SP00.A?lastNObservations=1&format=jsondata
    private String buildSeriesKey(SupportedCurrency from, SupportedCurrency to) {
        return String.join(".",
                FREQ,
                from.getIsoCode().toUpperCase(),
                to.getIsoCode().toUpperCase(),
                EXR_TYPE,
                EXR_SUFFIX);
    }
}
