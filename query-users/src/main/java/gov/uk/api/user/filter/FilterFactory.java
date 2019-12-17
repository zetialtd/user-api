package gov.uk.api.user.filter;

import gov.uk.api.user.geo.Coordinates;
import gov.uk.api.user.geo.CoordinatesRegistry;
import gov.uk.api.user.geo.DistanceCalculator;
import gov.uk.api.user.model.User;

import java.util.function.Predicate;

public class FilterFactory {
    private final CoordinatesRegistry coordinatesRegistry;
    private final DistanceCalculator distanceCalculator;

    public FilterFactory(CoordinatesRegistry coordinatesRegistry, DistanceCalculator distanceCalculator) {
        this.coordinatesRegistry = coordinatesRegistry;
        this.distanceCalculator = distanceCalculator;
    }

    public Predicate<User> newGeographicRadiusFilter(String location, int radiusLimitInMiles) {
        Coordinates startPoint = coordinatesRegistry.getForLocation(location);
        return new GeographicRadiusFilter(distanceCalculator, startPoint, radiusLimitInMiles);
    }
}
