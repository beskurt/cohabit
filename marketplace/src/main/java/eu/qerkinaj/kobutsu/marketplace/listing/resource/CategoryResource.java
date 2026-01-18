package eu.qerkinaj.kobutsu.marketplace.listing.resource;

import eu.qerkinaj.kobutsu.common.Role;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.CategoryBaseDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.CategoryDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.service.CategoryService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/v1/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @GET
    public List<CategoryDTO> all() {
        return categoryService.getAllCategories();
    }

    @GET
    @Path("/{id}")
    public CategoryDTO getCategory(@PathParam("id") UUID categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @POST
    @RolesAllowed(Role.ADMIN)
    public CategoryDTO createCategory(@Valid CategoryBaseDTO categoryBaseDto) {
        return categoryService.create(categoryBaseDto, null); //TODO: add option for children add
    }

    @DELETE
    @RolesAllowed(Role.ADMIN)
    @Path("/{id}")
    public void deleteCategory(@PathParam("id") UUID categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    @PUT
    @RolesAllowed(Role.ADMIN)
    @Path("/{id}")
    public void updateCategory(@PathParam("id") UUID categoryId, @Valid CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryId, categoryDTO);
    }

}