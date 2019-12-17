package gov.uk.api.gateway;

import gov.uk.api.user.model.User;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomUtils.nextDouble;
import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {
    private static final long THE_ID = nextLong();
    private static final String THE_EMAIL = "email-" + nextLong();
    public static final String THE_FIRST_NAME = "first_name-" + nextLong();
    public static final String THE_LAST_NAME = "last_name-" + nextLong();
    public static final String THE_IP_ADDRESS = "ip_address-" + nextLong();
    public static final double THE_LATITUDE = nextDouble();
    public static final double THE_LONGITUDE = nextDouble();

    @Test
    void mapsDtoToUser() {
        UserDto theDto = new UserDto();
        theDto.setId(THE_ID);
        theDto.setEmail(THE_EMAIL);
        theDto.setFirst_name(THE_FIRST_NAME);
        theDto.setLast_name(THE_LAST_NAME);
        theDto.setIp_address(THE_IP_ADDRESS);
        theDto.setLatitude(THE_LATITUDE);
        theDto.setLongitude(THE_LONGITUDE);

        User theUser = new UserMapper().toUser(theDto);

        assertThat(theUser.getId()).isEqualTo(THE_ID);
        assertThat(theUser.getEmailAddress()).isEqualTo(THE_EMAIL);
        assertThat(theUser.getFirstName()).isEqualTo(THE_FIRST_NAME);
        assertThat(theUser.getLastName()).isEqualTo(THE_LAST_NAME);
        assertThat(theUser.getIpAddress()).isEqualTo(THE_IP_ADDRESS);
        assertThat(theUser.getLatitudeDegrees()).isEqualTo(THE_LATITUDE);
        assertThat(theUser.getLongitudeDegrees()).isEqualTo(THE_LONGITUDE);
    }
}