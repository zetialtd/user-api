package gov.uk.api.user.model;

import static org.apache.commons.lang3.RandomUtils.nextDouble;
import static org.apache.commons.lang3.RandomUtils.nextLong;

public class TestData {
    private TestData() {
    }

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private long id;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String ipAddress;
        private double latitude;
        private double longitude;

        private UserBuilder() {
            id = nextLong();
            firstName = "firstName-" + nextLong();
            lastName = "lastName-" + nextLong();
            emailAddress = "emailAddress-" + nextLong();
            ipAddress = "ipAddress-" + nextLong();
            latitude = nextDouble();
            longitude = nextDouble();
        }

        public User build() {
            return new User(id, firstName, lastName, emailAddress, ipAddress, latitude, longitude);
        }

        public UserBuilder withId(long id) {
            this.id = id;
            return this;
        }
        
        public UserBuilder withLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public UserBuilder withLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }
    }
}
