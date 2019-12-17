package gov.uk.api.user.geo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoordinatesRegistryTest {
    private static final double OFFSET = 0.00001d;

    private CoordinatesRegistry coordinatesRegistry;

    @BeforeEach
    void setUp() {
        coordinatesRegistry = new CoordinatesRegistry();
    }

    @ParameterizedTest
    @CsvSource({"London,51.5085297,-0.12574"})
    void coordinatesForLocation(String city, double expectedLatitude, double expectedLongitude) {
        Coordinates coordinates = coordinatesRegistry.getForLocation(city);

        assertThat(coordinates.getLatitude()).isCloseTo(expectedLatitude, offset(OFFSET));
        assertThat(coordinates.getLongitude()).isCloseTo(expectedLongitude, offset(OFFSET));
    }

    @Test
    void unknownLocation() {
        CoordinatesNotFoundException exception = assertThrows(CoordinatesNotFoundException.class, () -> coordinatesRegistry.getForLocation("unknown"));

        assertThat(exception).hasMessage("Could not find coordinates for location: [unknown]");
    }
}