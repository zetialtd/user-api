package gov.uk.api.user.geo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class DistanceCalculatorTest {
    @ParameterizedTest
    @CsvSource(value = {
            "0,0,0,0,0",
            "1,0,0,0,69.09",
            "0,1,0,0,69.09",
            "0,0,1,0,69.09",
            "0,0,0,1,69.09",
            "-1,0,0,0,69.09",
            "0,-1,0,0,69.09",
            "0,0,-1,0,69.09",
            "0,0,0,-1,69.09",
            "2,0,0,0,138.18",
            "0,2,0,0,138.18",
            "0,0,2,0,138.18",
            "0,0,0,2,138.18",
            "-2,0,0,0,138.18",
            "0,-2,0,0,138.18",
            "0,0,-2,0,138.18",
            "90,0,0,0,6218.46",
            "0,0,90,0,6218.46",
            "-90,0,0,0,6218.46",
            "0,0,-90,0,6218.46",
            "-90,0,90,0,12436.93",
            "90,0,-90,0,12436.93",
            "0,180,0,0,12436.93",
            "0,0,0,180,12436.93"
    })
    void calculatesDistanceInMiles(Double latA, Double lngA, Double latB, Double lngB, Double expectedDistance) {
        Coordinates pointA = new Coordinates(latA, lngA);
        Coordinates pointB = new Coordinates(latB, lngB);

        Double actualDistance = new DistanceCalculator().milesBetween(pointA, pointB);

        assertThat(actualDistance).isCloseTo(expectedDistance, offset(0.01d));
    }
}