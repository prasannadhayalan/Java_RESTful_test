package user;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private Double latitude;
    private Double longitude;

    public User(String id, String firstName, String lastName, Double latitude, Double longitude) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public Double getLatitude() { return this.latitude; }
    public Double getLongitude() { return this.longitude; }



}
