package gov.uk.api.gateway;

import gov.uk.api.user.model.User;

public class UserMapper {
    public User toUser(UserDto dto) {
        return new User(dto.getId(),
                dto.getFirst_name(),
                dto.getLast_name(),
                dto.getEmail(),
                dto.getIp_address(),
                dto.getLatitude(),
                dto.getLongitude());
    }
}
