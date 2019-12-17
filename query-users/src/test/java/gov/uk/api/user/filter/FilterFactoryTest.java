package gov.uk.api.user.filter;

import gov.uk.api.user.geo.CoordinatesRegistry;
import gov.uk.api.user.geo.DistanceCalculator;
import gov.uk.api.user.geo.Coordinates;
import gov.uk.api.user.model.User;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Predicate;

import static gov.uk.api.user.geo.TestData.someCoordinates;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilterFactoryTest {
    @Mock
    private CoordinatesRegistry coordinatesRegistry;
    @Mock
    private DistanceCalculator distanceCalculator;
    @InjectMocks
    private FilterFactory factory;

    @Test
    void looksUpLocationCoordinatesForGeoRadiusFilter() {
        Coordinates expectedStartPoint = someCoordinates().build();
        when(coordinatesRegistry.getForLocation("theLocation")).thenReturn(expectedStartPoint);

        Predicate<User> filter = factory.newGeographicRadiusFilter("theLocation", 17);

        assertThat(filter).isInstanceOf(GeographicRadiusFilter.class);
        new ObjectAssert<>(filter).hasFieldOrPropertyWithValue("startPoint", expectedStartPoint);
    }

    @Test
    void setsRadiusLimitForGeoRadiusFilter() {
        Predicate<User> filter = factory.newGeographicRadiusFilter("theLocation", 17);

        assertThat(filter).isInstanceOf(GeographicRadiusFilter.class);
        new ObjectAssert<>(filter).hasFieldOrPropertyWithValue("radiusLimitInMiles", 17);
    }

    @Test
    void setsDistanceCalculatorForGeoRadiusFilter() {
        Predicate<User> filter = factory.newGeographicRadiusFilter("theLocation", 17);

        assertThat(filter).isInstanceOf(GeographicRadiusFilter.class);
        new ObjectAssert<>(filter).hasFieldOrPropertyWithValue("distanceCalculator", distanceCalculator);
    }
}