package gov.uk.api.user.datasource;

import gov.uk.api.user.model.User;

public interface UserDataSource {
    Iterable<User> findByCity(String city);

    Iterable<User> getAll();
}
