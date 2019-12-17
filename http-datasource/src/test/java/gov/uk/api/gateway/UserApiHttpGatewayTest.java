package gov.uk.api.gateway;

import gov.uk.api.user.datasource.UserDataSource;
import gov.uk.api.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static gov.uk.api.user.model.TestData.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserApiHttpGatewayTest {
    private static final String THE_BASE_URI = "theBaseUri";

    @Mock
    private UserMapper userMapper;
    @Mock
    private RestTemplateBuilder restTemplateBuilder;
    @Mock
    private RestTemplate restTemplate;
    private UserDataSource gateway;

    @BeforeEach
    void setUp() {
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        gateway = new UserApiHttpGateway(THE_BASE_URI, restTemplateBuilder, userMapper);
    }

    @Test
    void findUsersByCity() {
        UserDto user1Dto = new UserDto();
        UserDto user2Dto = new UserDto();
        when(restTemplate.getForObject(THE_BASE_URI + "/city/{city}/users", UserDto[].class, "theCity")).thenReturn(new UserDto[]{user1Dto, user2Dto});

        User user1 = aUser().build();
        User user2 = aUser().build();
        when(userMapper.toUser(user1Dto)).thenReturn(user1);
        when(userMapper.toUser(user2Dto)).thenReturn(user2);

        Iterable<User> actualUsers = gateway.findByCity("theCity");

        assertThat(actualUsers).containsExactly(user1, user2);
    }

    @Test
    void getAllUsers() {
        UserDto user1Dto = new UserDto();
        UserDto user2Dto = new UserDto();
        when(restTemplate.getForObject(THE_BASE_URI + "/users", UserDto[].class)).thenReturn(new UserDto[]{user1Dto, user2Dto});

        User user1 = aUser().build();
        User user2 = aUser().build();
        when(userMapper.toUser(user1Dto)).thenReturn(user1);
        when(userMapper.toUser(user2Dto)).thenReturn(user2);

        Iterable<User> actualUsers = gateway.getAll();

        assertThat(actualUsers).containsExactly(user1, user2);
    }
}
