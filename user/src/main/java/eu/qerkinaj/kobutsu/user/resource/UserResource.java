package eu.qerkinaj.kobutsu.user.resource;

import eu.qerkinaj.kobutsu.common.Role;
import eu.qerkinaj.kobutsu.user.dto.UserDTO;
import eu.qerkinaj.kobutsu.user.service.UserService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @RolesAllowed(Role.USER)
    @Path("/me")
    public UserDTO me() {
        UUID uuid = UUID.fromString(securityIdentity.getAttribute("userId").toString());
        return userService.getUser(uuid);
    }

    @GET
    @Path("/{userid}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUserEmail(@PathParam("userid") String id) {
        return userService.getUserEmail(UUID.fromString(id));
    }

}
