package eu.qerkinaj.kobutsu.marketplace.listing.resource;

import eu.qerkinaj.kobutsu.common.Role;
import eu.qerkinaj.kobutsu.marketplace.currency.SupportedCurrency;
import eu.qerkinaj.kobutsu.marketplace.currency.qualifier.CurrentCurrency;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.ListingBaseDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.ListingDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.ListingOrderDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.QueryParamsDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.service.ListingService;
import eu.qerkinaj.kobutsu.marketplace.listing.service.OrderService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListingResource {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    ListingService listingService;

    @Inject
    OrderService orderService;

    @Inject
    @CurrentCurrency
    SupportedCurrency supportedCurrency;

    @GET
    @Path("/listings")
    public List<ListingDTO> getListings() {
        return listingService.getAllListings(
                new QueryParamsDTO(
                        null,
                        supportedCurrency,
                        null,
                        0,
                        100)
        );
    }

    @POST
    @RolesAllowed(Role.USER)
    @Path("/listings")
    public ListingOrderDTO create(@Valid ListingBaseDTO listingDTO) {
        return listingService.create(listingDTO, currentUserId());

    }

    @POST
    @RolesAllowed(Role.USER)
    @Path("/listings/{listingId}/buy")
    public void buy(@PathParam("listingId") UUID listingId) {
        orderService.buy(listingId, currentUserId());
    }

    @GET
    @Path("/listings/{listingId}")
    public ListingOrderDTO getListing(@PathParam("listingId") UUID listingId) {
        return listingService.getListing(listingId);
    }

    @PUT
    @RolesAllowed(Role.USER)
    @Path("/listings/{listingId}")
    public ListingOrderDTO updateListing(@PathParam("listingId") UUID listingId, @Valid ListingBaseDTO listingDTO) {
        return listingService.update(listingId, listingDTO);
    }

    //TODO: check if i need to return something
    @DELETE
    @RolesAllowed(Role.USER)
    @Path("/listings/my/{listingId}")
    public void deleteListing(@PathParam("listingId") UUID listingId) {
        listingService.delete(listingId, currentUserId());
    }


    @GET
    @RolesAllowed(Role.USER)
    @Path("/listings/my")
    public List<ListingOrderDTO> getMyListings() {
        return listingService.getSellersListings(currentUserId());
    }

    private UUID currentUserId() {
        return UUID.fromString(securityIdentity.getAttribute("userId").toString());
    }


}

