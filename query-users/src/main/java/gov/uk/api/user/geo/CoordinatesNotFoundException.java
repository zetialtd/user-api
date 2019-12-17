package gov.uk.api.user.geo;

public class CoordinatesNotFoundException extends RuntimeException {
    public CoordinatesNotFoundException(String message) {
        super(message);
    }
}
