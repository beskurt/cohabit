package eu.qerkinaj.kobutsu.user.resource;

import eu.qerkinaj.kobutsu.user.dto.LoginRequest;
import eu.qerkinaj.kobutsu.user.dto.TokenResponse;
import eu.qerkinaj.kobutsu.user.dto.UserDTO;
import eu.qerkinaj.kobutsu.user.service.LoginService;
import eu.qerkinaj.kobutsu.user.service.RegistrationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private static final Logger log = LoggerFactory.getLogger(AuthResource.class);

    @Inject
    LoginService loginService;

    @Inject
    RegistrationService registrationService;

    @POST
    @Path("/login")
    public Response login(LoginRequest loginRequest) {
        try {
            TokenResponse token = loginService.login(loginRequest);
            return Response.ok(token).build();
        }
        catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/register")
    public Response register(UserDTO userDTO) {
        try {
            UserDTO user = registrationService.register(userDTO);

            log.debug("Registered user {}", user);

            return Response.ok().build();
        }
        catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
