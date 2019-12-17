package gov.uk.api.user.query;

import gov.uk.api.user.datasource.UserDataSource;
import gov.uk.api.user.filter.FilterFactory;
import gov.uk.api.user.model.User;

import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class UserQuery {
    private UserDataSource userDataSource;
    private FilterFactory filterFactory;

    public UserQuery(UserDataSource userDataSource, FilterFactory filterFactory) {
        this.userDataSource = userDataSource;
        this.filterFactory = filterFactory;
    }

    public Iterable<User> findByCity(String city) {
        return userDataSource.findByCity(city);
    }

    public Iterable<User> findByLocationAndRadius(String location, int radiusInMiles) {
        Predicate<User> geographicRadiusFilter = filterFactory.newGeographicRadiusFilter(location, radiusInMiles);
        Iterable<User> allUsers = userDataSource.getAll();
        return StreamSupport.stream(allUsers.spliterator(), false).filter(geographicRadiusFilter).collect(toList());
    }

    public Iterable<User> getAll() {
        return userDataSource.getAll();
    }
}
