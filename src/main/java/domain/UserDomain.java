package domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

import user.User;

@Document(collection = UserDomain.COLLECTION_NAME)
public class UserDomain {
    public static final String COLLECTION_NAME = "User";

    public  String id;
    public static final String ID_FIELD = "_id";

    private  String firstName;
    public static final String FIRST_NAME = "firstName";

    private  String lastName;
    public static final String LAST_NAME = "lastName";

    private  double latitude;
    public static final String LATITUDE = "latitude";

    private  double longitude;
    public static final String LONGITUDE = "longitude";

    public UserDomain(){
    }

    public UserDomain( String firstName, String lastName, double latitude, double longitude) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UserDomain(User user) {
       setId(user.getId());
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.latitude = user.getLatitude();
        this.longitude = user.getLongitude();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public User toModel() {
        return new User(getId(), firstName, lastName, latitude, longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), firstName, lastName, latitude, longitude);
    }

    public boolean equals(UserDomain userDomain) {

        if (userDomain == null || userDomain.getClass() != this.getClass()) {
            return false;
        }
        if (this == userDomain || this.hashCode() == userDomain.hashCode()) {
            return true;
        }

        return Objects.equals(this.getId(), userDomain.getId()) &&
                Objects.equals(this.getFirstName(), userDomain.getFirstName()) &&
                Objects.equals(this.getLastName(), userDomain.getLastName()) &&
                Objects.equals(this.getLatitude(), userDomain.getLatitude()) &&
                Objects.equals(this.getLongitude(), userDomain.getLongitude());
    }
}
