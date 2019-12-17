package gov.uk.api.gateway;

import gov.uk.api.user.datasource.UserDataSource;
import gov.uk.api.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

@Component
public class UserApiHttpGateway implements UserDataSource {
    private String baseUri;
    private final RestTemplate restTemplate;
    private UserMapper userMapper;

    public UserApiHttpGateway(@Value("${datasource.http.base.uri}") String baseUri, RestTemplateBuilder restTemplateBuilder, UserMapper userMapper) {
        this.baseUri = baseUri;
        this.restTemplate = restTemplateBuilder.build();
        this.userMapper = userMapper;
    }

    @Override
    public Iterable<User> findByCity(String city) {
        UserDto[] userDtos = restTemplate.getForObject(baseUri + "/city/{city}/users", UserDto[].class, city);
        return Arrays.stream(userDtos).map(userMapper::toUser).collect(toList());
    }

    @Override
    public Iterable<User> getAll() {
        UserDto[] userDtos = restTemplate.getForObject(baseUri + "/users", UserDto[].class);
        return Arrays.stream(userDtos).map(userMapper::toUser).collect(toList());
    }
}
