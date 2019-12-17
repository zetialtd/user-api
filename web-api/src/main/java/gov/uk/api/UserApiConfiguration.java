package gov.uk.api;

import gov.uk.api.user.datasource.UserDataSource;
import gov.uk.api.user.filter.FilterFactory;
import gov.uk.api.user.geo.CoordinatesRegistry;
import gov.uk.api.user.geo.DistanceCalculator;
import gov.uk.api.user.query.UserQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserApiConfiguration {
    @Bean
    public UserQuery userQuery(UserDataSource userDataSource) {
        return new UserQuery(userDataSource, new FilterFactory(new CoordinatesRegistry(), new DistanceCalculator()));
    }
}
