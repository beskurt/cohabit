package eu.qerkinaj.kobutsu.marketplace.listing.service;

import eu.qerkinaj.kobutsu.marketplace.listing.domain.Listing;
import eu.qerkinaj.kobutsu.marketplace.listing.domain.Order;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.OrderBuyerDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.mapper.OrderMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Inject
    OrderMapper orderMapper;

    @Transactional
    public void buy(UUID listingId, UUID buyerId) {
        log.debug("Buying order with listing id {} and buyer id {}", listingId, buyerId);
        Listing listing = Listing.findById(listingId);

        if (listing == null) {
            log.error("Listing with id {} not found", listingId);
            throw new NotFoundException("Listing not found");
        }
        if (listing.sellerId.equals(buyerId)) {
            log.error("Cannot buy your own listing");
            throw new WebApplicationException("Cannot buy your own listing", 400);
        }

        Order order = new Order();
        order.listing = listing;
        order.buyerId = buyerId;
        listing.orders.add(order);
        order.persist();
    }

    public List<OrderBuyerDTO> getBuyersOrder(UUID buyerId) {
        log.debug("Getting buyer orders for listing with id {}", buyerId);
        return orderMapper.toBuyerDto(Order.findOrdersByBuyerId(buyerId));
    }
}
