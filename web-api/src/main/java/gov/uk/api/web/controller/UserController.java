package gov.uk.api.web.controller;

import gov.uk.api.user.model.User;
import gov.uk.api.user.query.UserQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    private static final Integer DEFAULT_RADIUS = 1;

    private final UserQuery userQuery;

    public UserController(UserQuery userQuery) {
        this.userQuery = userQuery;
    }

    @Operation(summary = "Find all users listed in a city.")
    @GetMapping(path = "/cities/{city}/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> findByCity(@PathVariable String city) {
        return userQuery.findByCity(city);
    }

    @Operation(summary = "Find users, optionally filtered by a location and radius.  If no location is provided then all users are returned")
    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> findByLocationAndRadius(
            @Parameter(description = "A location name. Currently only 'London' is supported") @RequestParam(name = "location", required = false) Optional<String> location,
            @Parameter(description = "Defines a radius around the coordinates of the supplied location, used to filter the returned users.  Ignored if 'location' not supplied") @RequestParam(name = "radiusInMiles", required = false) Optional<Integer> radiusLimit) {
        if (location.isPresent()) {
            return userQuery.findByLocationAndRadius(location.get(), radiusLimit.orElse(DEFAULT_RADIUS));
        }
        return userQuery.getAll();
    }
}
