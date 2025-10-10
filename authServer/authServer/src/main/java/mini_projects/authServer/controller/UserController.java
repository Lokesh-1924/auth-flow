package mini_projects.authServer.controller;

import mini_projects.authServer.entity.User;
import mini_projects.authServer.service.UserService;
import org.apache.catalina.startup.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping(path = "/register", consumes = "application/json")
    public ResponseEntity<?> addUser( @RequestBody User user){
        System.out.println("Received user: " + user.getUsername());
        try{
            userService.addUser(user);
            User savedUser = userService.addUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println("Exception : "+e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-users")
    public ResponseEntity<?> getUsers(){
        try {
            List<User> allUsers = userService.getAllActiveUsers();
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
