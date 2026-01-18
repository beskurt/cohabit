package eu.qerkinaj.kobutsu.marketplace.listing.service;

import eu.qerkinaj.kobutsu.marketplace.currency.ecb.EcbDataService;
import eu.qerkinaj.kobutsu.marketplace.listing.domain.Category;
import eu.qerkinaj.kobutsu.marketplace.listing.domain.Listing;
import eu.qerkinaj.kobutsu.marketplace.listing.domain.ListingImage;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.ListingBaseDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.ListingDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.ListingOrderDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.QueryParamsDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.mapper.ListingImageMapper;
import eu.qerkinaj.kobutsu.marketplace.listing.mapper.ListingMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class ListingService {

    private static final Logger log = LoggerFactory.getLogger(ListingService.class);

    @Inject
    ListingMapper listingMapper;

    @Inject
    ListingImageMapper listingImageMapper;

    @Inject
    EcbDataService ecbDataService;

    @Transactional
    public ListingOrderDTO create(ListingBaseDTO creation, UUID userId) {
        log.debug("Create listing: {}", creation);

        Listing listing = listingMapper.toEntity(creation);
        listing.sellerId = userId;

        Category category = Category.findByName(creation.getCategory().getName());
        if (category == null) {
            log.error("Category not found");
            throw new NotFoundException("Category not found");
        }

        log.debug("found category: {}", category);
        listing.category = category;

        List<ListingImage> images = listingImageMapper.toEntity(creation.getImages());
        if (images != null) {
            log.debug("converting images: {}", images.size());
            for (ListingImage image : images) {
                log.debug("converting image dto to entity: {}", image);
                image.listing = listing;
            }
            listing.images.addAll(images);
        }

        listing.persist();
        log.debug("listing saved: {}", listing);

        return listingMapper.toDtoComplete(listing);
    }

    @Transactional
    public ListingOrderDTO update(UUID listingId, ListingBaseDTO listingDTO) {
        Listing existing = Listing.findById(listingId);
        if (existing == null) {
            log.error("Listing not found: {}", listingId);
            throw new NotFoundException("Listing not found: " + listingId);
        }
        log.debug("Update listing: {}", listingDTO);
        listingMapper.updateEntityFromDto(listingDTO, existing);
        existing.persist();
        return listingMapper.toDtoComplete(existing);
    }

    @Transactional
    public void delete(UUID listingId, UUID userId) {
        log.debug("Delete listing: {}, userId: {}", listingId, userId);
        Listing existing = Listing.findById(listingId);
        if (existing == null) {
            log.error("Listing not found: {}", listingId);
            throw new NotFoundException("Listing not found: " + listingId);
        }

        //TODO: check besser machen
        if (existing.sellerId.equals(userId)) {
            existing.delete();
            log.info("Deleted listing {}", listingId);
        }
    }

    public ListingOrderDTO getListing(UUID listingId) {
        return listingMapper.toDtoComplete(Listing.findById(listingId));
    }

    public List<ListingOrderDTO> getSellersListings(UUID sellerId) {
        return listingMapper.toDtoCompleteList(Listing.findBySellerId(sellerId));
    }

    public List<ListingDTO> getAllListings(QueryParamsDTO params) {
        StringBuilder whereClause = new StringBuilder();
        List<Object> args = new ArrayList<>();
        List<ListingDTO> dtos;

        if (params.condition() != null) {
            if (!whereClause.isEmpty()) whereClause.append(" and ");
            whereClause.append("condition = ?").append(1);
            args.add(params.condition());
        }

        if (params.category() != null && !params.category().isBlank()) {
            if (!whereClause.isEmpty()) whereClause.append(" and ");
            whereClause.append("category = ?").append(args.size() + 1);
            args.add(Category.findByName(params.category()));
        }

        String jpql = whereClause.isEmpty() ? "1 = 1" : whereClause.toString();
        PanacheQuery<Listing> query;

        int page = Math.max(0, params.page());
        int size = Math.max(1, params.size());

//        if (params.sortBy() != null) {
//            Sort sort = switch (params.sortBy()) {
//                case PRICE_ASC -> Sort.by("price.amount");
//                case PRICE_DESC -> Sort.by("price.amount").descending();
//                case NEWEST -> Sort.by("createdAt").descending();
//                case OLDEST -> Sort.by("createdAt");
//                default -> Sort.empty();
//            };
//            query = Listing.find(
//                    jpql,
//                    args.toArray(),
//                    sort
//            ).page(Page.of(page, size));
//        } else {
//            query = Listing.find(
//                    jpql,
//                    args.toArray()
//            ).page(Page.of(page, size));
//        }

        query = Listing.find(
                jpql,
                args.toArray()
        ).page(Page.of(page, size));

        dtos = listingMapper.toDtoList(query.list());

        if (params.currency() != null) {
            return ecbDataService.convertCurrency(dtos, params.currency());
        }

        return dtos;
    }

}
