package gov.uk.api.user.geo;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class CoordinatesRegistry {
    private static final String NOT_FOUND_MESSAGE = "Could not find coordinates for location: [%s]";

    private static final Map<String, Coordinates> LOCATION_COORDINATES = new HashMap<>();

    static {
        LOCATION_COORDINATES.put("London", new Coordinates(51.5085297, -0.12574));
    }

    public Coordinates getForLocation(String city) {
        if (!LOCATION_COORDINATES.containsKey(city)) {
            throw new CoordinatesNotFoundException(format(NOT_FOUND_MESSAGE, city));
        }
        return LOCATION_COORDINATES.get(city);
    }
}
