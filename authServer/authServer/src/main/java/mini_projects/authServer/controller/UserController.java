package mini_projects.authServer.controller;

import mini_projects.authServer.entity.User;
import mini_projects.authServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;
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
}
