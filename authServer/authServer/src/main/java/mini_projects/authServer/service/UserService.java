package mini_projects.authServer.service;

import mini_projects.authServer.entity.User;
import mini_projects.authServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        try{
            userRepository.save(user);
            return user;
        }
        catch (Exception e){
            System.out.println("Exception : "+e);
            return null;
        }
    }
}
