package gov.uk.api.user.geo;

import static org.apache.commons.lang3.RandomUtils.nextDouble;

public class TestData {
    private TestData() {
    }

    public static CoordinatesBuilder someCoordinates() {
        return new CoordinatesBuilder();
    }

    public static class CoordinatesBuilder {
        private double latitude = nextDouble();
        private double longitude = nextDouble();

        private CoordinatesBuilder() {

        }

        public CoordinatesBuilder withLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public CoordinatesBuilder withLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Coordinates build() {
            return new Coordinates(latitude, longitude);
        }
    }

}
