package gov.uk.api.user.query;

import gov.uk.api.user.datasource.UserDataSource;
import gov.uk.api.user.filter.FilterFactory;
import gov.uk.api.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Predicate;

import static gov.uk.api.user.model.TestData.aUser;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserQueryTest {
    @Mock
    private FilterFactory filterFactory;
    @Mock
    private UserDataSource userDataSource;
    @InjectMocks
    private UserQuery userQuery;

    @Test
    void findUsersByCity() {
        User user1 = aUser().build();
        User user2 = aUser().build();
        when(userDataSource.findByCity("theCity")).thenReturn(asList(user1, user2));

        Iterable<User> theUsers = userQuery.findByCity("theCity");

        assertThat(theUsers).containsExactly(user1, user2);
    }

    @Test
    void findUsersByLocationAndRadius_usesFilterAgainstAllUsers() {
        User user1 = aUser().build();
        User user2 = aUser().build();
        when(userDataSource.getAll()).thenReturn(asList(user1, user2));

        Predicate<User> theFilter = mock(Predicate.class);
        when(filterFactory.newGeographicRadiusFilter("theCity", 17)).thenReturn(theFilter);

        userQuery.findByLocationAndRadius("theCity", 17);

        verify(theFilter).test(user1);
        verify(theFilter).test(user2);
    }

    @Test
    void findUsersByLocationAndRadius_removesNonMatchingUsers() {
        User user1 = aUser().build();
        User user2 = aUser().build();
        User user3 = aUser().build();
        when(userDataSource.getAll()).thenReturn(asList(user1, user2, user3));

        Predicate<User> theFilter = mock(Predicate.class);
        when(filterFactory.newGeographicRadiusFilter(anyString(), anyInt())).thenReturn(theFilter);
        when(theFilter.test(user1)).thenReturn(true);
        when(theFilter.test(user2)).thenReturn(false);
        when(theFilter.test(user3)).thenReturn(true);

        Iterable<User> actualUsers = userQuery.findByLocationAndRadius("theCity", 17);

        assertThat(actualUsers).containsExactly(user1, user3);
    }

    @Test
    void getAllUsers() {
        User user1 = aUser().build();
        User user2 = aUser().build();
        when(userDataSource.getAll()).thenReturn(asList(user1, user2));

        Iterable<User> actualUsers = userQuery.getAll();

        assertThat(actualUsers).containsExactly(user1, user2);
    }
}
