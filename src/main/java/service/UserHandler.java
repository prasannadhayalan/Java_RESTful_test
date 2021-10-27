package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import domain.UserDomain;
import repository.UserRepository;
import user.NearByUsersDetails;
import user.User;

@Service
public class UserHandler {

    @Autowired
    UserRepository userRepository;

    public List<User> users() {
        List<User> userList = userRepository.users().stream().map(p -> p.toModel()).collect(Collectors.toList());
        return userList.isEmpty() ? null : userList;
    }


    public User user(User input) {
        UserDomain userDomain =
                new UserDomain(input);
        userRepository.createUser(userDomain);
        return userDomain.toModel();
    }

    public User getUser(String userId) {
        UserDomain userDomain = userRepository.findUser(userId);
        return userDomain.toModel();
    }

    public User updateUser(String userId, User input) {
        UserDomain userDomain =
                new UserDomain(input);
        userRepository.updateUser(userId, userDomain);
        return userDomain.toModel();
    }

    public void deleteUser(String userId) {
         userRepository.deleteUser(userId);;
    }

    public String   getDistances() {
        List<User> userList = userRepository.users().stream().map(p -> p.toModel()).collect(Collectors.toList());
        return calculateDistance(userList);
    }

    private static String calculateDistance(List<User> users) {
        if (users == null || users.isEmpty() || users.size() == 1) {
            return null;
        }
        Map<String, NearByUsersDetails> distanceCalculatedByUser = new HashMap<>();
        for (int i = 0; i < users.size(); ++i) {
            for (int j = i + 1; j < users.size(); j++) {
                User user1 = users.get(i);
                User user2 = users.get(j);
                CalculateDistance(user1, user2, distanceCalculatedByUser);
            }
        }
        StringBuffer data = new StringBuffer();
        data.append("{ userList : [ ");
       List<NearByUsersDetails> nearByUsersDetailsList = new ArrayList<>(distanceCalculatedByUser.values());
        for (int i = 0; i < nearByUsersDetailsList.size(); i++) {
            data.append( nearByUsersDetailsList.get(i));
            if(nearByUsersDetailsList.size()-1 > i){
                data.append( ",");
            }
        }
        data.append("]}");
        return data.toString();
    }

    private static void CalculateDistance(User user1, User user2,
                                          Map<String, NearByUsersDetails> distanceCalculatedByUser) {
        NearByUsersDetails userDetails =
                distanceCalculatedByUser.computeIfAbsent(user1.getId(), v -> new NearByUsersDetails());
        NearByUsersDetails userDetails2 =
                distanceCalculatedByUser.computeIfAbsent(user2.getId(), v -> new NearByUsersDetails());
        double distance =
                calculateDistance(user1.getLatitude(), user1.getLongitude(), user2.getLatitude(), user2.getLongitude());
        userDetails.setUserDetails(user1);
        updateUserDetails(userDetails, user2, distance);
        updateUserDetails(userDetails2, user1, distance);
        userDetails2.setUserDetails(user2);
    }

    private static void updateUserDetails(NearByUsersDetails userDetails, User user, double distance) {
        if (userDetails.getMin() == 0 || userDetails.getMin() > distance) {
            userDetails.setMin(distance);
            userDetails.setNearestUser(user.getFirstName());
        }
        if (userDetails.getMax() < distance) {
            userDetails.setMax(distance);
            userDetails.setFarestUser(user.getFirstName());
        }
        userDetails.setTotal(userDetails.getTotal() + distance);
    }

    public static double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371.0d; // KM: use mile here if you want mile result

        double dLat = toRadian(lat2 - lat1);
        double dLng = toRadian(lng2 - lng1);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(toRadian(lat1)) * Math.cos(toRadian(lat2)) *
                Math.pow(Math.sin(dLng / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c; // returns result kilometers
    }

    public static double toRadian(double degrees) {
        return (degrees * Math.PI) / 180.0d;
    }
}
