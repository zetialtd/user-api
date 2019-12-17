package gov.uk.api.user.filter;

import gov.uk.api.user.geo.Coordinates;
import gov.uk.api.user.geo.DistanceCalculator;
import gov.uk.api.user.model.User;

import java.util.function.Predicate;

public class GeographicRadiusFilter implements Predicate<User> {
    private final DistanceCalculator distanceCalculator;
    private final Coordinates startPoint;
    private final int radiusLimitInMiles;

    public GeographicRadiusFilter(DistanceCalculator distanceCalculator, Coordinates startPoint, int radiusLimitInMiles) {
        this.distanceCalculator = distanceCalculator;
        this.startPoint = startPoint;
        this.radiusLimitInMiles = radiusLimitInMiles;
    }

    @Override
    public boolean test(User user) {
        Double userDistance = distanceCalculator.milesBetween(startPoint, new Coordinates(user.getLatitudeDegrees(), user.getLongitudeDegrees()));
        return userDistance <= radiusLimitInMiles;
    }
}
