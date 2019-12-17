package gov.uk.api.web.controller;

import gov.uk.api.user.model.User;
import gov.uk.api.user.query.UserQuery;
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

    @GetMapping(path = "/cities/{city}/users")
    public Iterable<User> findByCity(@PathVariable String city, @RequestParam(name = "radiusInMiles") Optional<Integer> radiusLimit) {
        return userQuery.findByCity(city);
    }

    @GetMapping(path = "/users")
    public Iterable<User> findByLocationAndRadius(@RequestParam(name = "location") Optional<String> location, @RequestParam(name = "radiusInMiles") Optional<Integer> radiusLimit) {
        if (location.isPresent()) {
            return userQuery.findByLocationAndRadius(location.get(), radiusLimit.orElse(DEFAULT_RADIUS));
        }
        return userQuery.getAll();
    }
}
