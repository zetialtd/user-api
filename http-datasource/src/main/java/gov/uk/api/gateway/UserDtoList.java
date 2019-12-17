package gov.uk.api.gateway;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class UserDtoList {
    private List<UserDto> userDtos;

    @JsonCreator
    public UserDtoList(List<UserDto> userDtos) {
        this.userDtos = userDtos;
    }

    public List<UserDto> getUserDtos() {
        return userDtos;
    }
}
