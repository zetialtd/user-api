package gov.uk.api.user.model;

public class User {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final String ipAddress;
    private final double latitudeDegrees;
    private final double longitudeDegrees;

    public User(long id, String firstName, String lastName, String emailAddress, String ipAddress, double latitudeDegrees, double longitudeDegrees) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.ipAddress = ipAddress;
        this.latitudeDegrees = latitudeDegrees;
        this.longitudeDegrees = longitudeDegrees;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public double getLatitudeDegrees() {
        return latitudeDegrees;
    }

    public double getLongitudeDegrees() {
        return longitudeDegrees;
    }
}
