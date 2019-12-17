package gov.uk.api.user.filter;

import gov.uk.api.user.geo.Coordinates;
import gov.uk.api.user.geo.DistanceCalculator;
import gov.uk.api.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Predicate;

import static gov.uk.api.user.geo.TestData.someCoordinates;
import static gov.uk.api.user.model.TestData.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeographicRadiusFilterTest {
    @Mock
    private DistanceCalculator distanceCalculator;

    @Test
    void usesDistanceCalculatorToFilterByRadius() {
        Coordinates theStartPoint = someCoordinates().build();

        Predicate<User> filter = new GeographicRadiusFilter(distanceCalculator, theStartPoint, 23);

        filter.test(aUser().withLatitude(1.1d).withLongitude(2.2d).build());

        verify(distanceCalculator, times(1)).milesBetween(same(theStartPoint), argThat(isCoordinatesOf(1.1d, 2.2d)));
    }

    @Test
    void excludesUserWhenLocatedOutsideRadius() {
        distanceCalculatorReturns(23.1d);

        Predicate<User> filter = aFilterWithRadiusLimit(23);

        assertThat(filter.test(aUser().build())).isFalse();
    }

    @Test
    void includesUserWhenLocatedOnRadius() {
        distanceCalculatorReturns(23.0d);

        Predicate<User> filter = aFilterWithRadiusLimit(23);

        assertThat(filter.test(aUser().build())).isTrue();
    }

    @Test
    void includesUserWhenLocatedInsideRadius() {
        distanceCalculatorReturns(22.9d);

        Predicate<User> filter = aFilterWithRadiusLimit(23);

        assertThat(filter.test(aUser().build())).isTrue();
    }

    private void distanceCalculatorReturns(double v) {
        when(distanceCalculator.milesBetween(any(Coordinates.class), any(Coordinates.class))).thenReturn(v);
    }

    private Predicate<User> aFilterWithRadiusLimit(int radiusLimit) {
        return new GeographicRadiusFilter(distanceCalculator, someCoordinates().build(), radiusLimit);
    }

    private ArgumentMatcher<Coordinates> isCoordinatesOf(double latitude, double longitude) {
        return (coordinates -> latitude == coordinates.getLatitude() && longitude == coordinates.getLongitude());
    }
}