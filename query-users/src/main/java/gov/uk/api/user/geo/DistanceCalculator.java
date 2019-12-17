package gov.uk.api.user.geo;


import static java.lang.Math.*;

public class DistanceCalculator {
    private static final double EARTH_RADIUS_IN_MILES = 3958.8d;

    /**
     * Calculates distance between two points using 'great circle distance' formula, assuming Earth's shape to be a sphere.
     *
     * @param pointA {@link Coordinates} instance start point.
     * @param pointB {@link Coordinates} instance end point.
     * @return Distance in miles between the two points along the Earth's surface.
     */
    public Double milesBetween(Coordinates pointA, Coordinates pointB) {
        double distanceInRadians = sphericalGreatCircleDistanceBetween(pointA, pointB);
        return distanceInRadians * EARTH_RADIUS_IN_MILES;
    }

    private double sphericalGreatCircleDistanceBetween(Coordinates pointA, Coordinates pointB) {
        double pointALatRads = toRadians(pointA.getLatitude());
        double pointBLatRads = toRadians(pointB.getLatitude());
        double latDeltaRads = abs(pointBLatRads - pointALatRads);
        double lonDeltaRads = abs(toRadians(pointB.getLongitude() - pointA.getLongitude()));

        double a = sin(latDeltaRads / 2) * sin(latDeltaRads / 2) + cos(pointALatRads) * cos(pointBLatRads) * sin(lonDeltaRads / 2) * sin(lonDeltaRads / 2);

        return 2 * atan2(sqrt(a), sqrt(1 - a));
    }
}
