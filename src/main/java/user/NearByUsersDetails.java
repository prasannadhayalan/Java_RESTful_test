package user;


public class NearByUsersDetails {
    private String nearestUser;
    private User userDetails;
    private double min;
    private String farestUser;
    private double max;
    private double total;
    private double std;

    public NearByUsersDetails(String nearestUser, double min, String farestUser, double max) {
        this.nearestUser = nearestUser;
        this.min = min;
        this.farestUser = farestUser;
        this.max = max;
    }

    public NearByUsersDetails() {
    }

    public String getNearestUser() {
        return nearestUser;
    }

    public void setNearestUser(String nearestUser) {
        this.nearestUser = nearestUser;
    }

    public String getFarestUser() {
        return farestUser;
    }

    public void setFarestUser(String farestUser) {
        this.farestUser = farestUser;
    }

    public double getStd() {
        return std;
    }

    public void setStd(double std) {
        this.std = std;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public String toString() {

        return String.format("{user : %s, min : %s, nearestUser: %s, max : %s,farestUser :%s}", userDetails.getFirstName(), min, nearestUser, max, farestUser);

    }


}
