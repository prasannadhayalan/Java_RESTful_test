package user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import service.UserHandler;

@RestController
@RequestMapping(value = "/user-service")
public class UserController {

    @Autowired
    UserHandler userHandler;

    @RequestMapping(method = RequestMethod.GET, value="/jrt/api/v1.0/users")
    public List<User> users() {
        return userHandler.users();
    }

    @RequestMapping(method = RequestMethod.GET, value="/jrt/api/v1.0/user")
    public User get_user(@PathVariable String userId) {
        return userHandler.getUser(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value="/jrt/api/v1.0/user")
    public User add_user(@RequestBody User input) {

        return userHandler.user(input);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/jrt/api/v1.0/user")
    public User update_user(@PathVariable String userId, @RequestBody User input) {
        return userHandler.updateUser(userId,input);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/jrt/api/v1.0/user")
    public void delete_user(@PathVariable String userId) {
         userHandler.deleteUser(userId);;
    }

    @RequestMapping(method = RequestMethod.GET, value="/jrt/api/v1.0/distances")
    public String distances() {
        return userHandler.getDistances();
    }
}
