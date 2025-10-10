package mini_projects.authServer.service;

import mini_projects.authServer.entity.User;
import mini_projects.authServer.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User addUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(List.of("USER"));
            userRepository.save(user);
            return user;
        }
        catch (Exception e){
            System.out.println("Exception : "+e);
            return null;
        }
    }

    public List<User> getAllActiveUsers(){
        try {
            List<User> allActiveUsers = userRepository.findAllActiveUsers();
            return allActiveUsers;
        }catch (Exception e){
            System.out.println(e);
            return new ArrayList<>();
        }
    }
}
