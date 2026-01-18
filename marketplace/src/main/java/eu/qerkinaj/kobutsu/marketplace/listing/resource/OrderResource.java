package eu.qerkinaj.kobutsu.marketplace.listing.resource;

import eu.qerkinaj.kobutsu.common.Role;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.OrderBuyerDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.service.OrderService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @GET
    @RolesAllowed(Role.USER)
    @Path("/orders/my")
    public List<OrderBuyerDTO> getMyOrders(@Context SecurityIdentity securityIdentity) {
        UUID buyerId = UUID.fromString(securityIdentity.getAttribute("userId"));
        return orderService.getBuyersOrder(buyerId);
    }


}
