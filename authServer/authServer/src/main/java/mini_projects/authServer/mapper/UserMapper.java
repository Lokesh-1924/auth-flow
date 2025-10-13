package mini_projects.authServer.mapper;

import mini_projects.authServer.dto.UserRequestDTO;
import mini_projects.authServer.dto.UserResponseDTO;
import mini_projects.authServer.entity.User;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
//Had to make it a service, cuz we cant create an object directly, as we need No args constructor to create an object,
//But the passwordEncoder bean will not be initialized if we add a no args constructor,
//Cant make the methods static as a method will use the passwordEncoder, and it is non-static
//Now with @Component, we can simply inject this dependency in service

//I encoded password in service in the code, earlier was encoding here

public class UserMapper {

//    private final PasswordEncoder passwordEncoder;
//    public UserMapper(PasswordEncoder passwordEncoder){
//        this.passwordEncoder = passwordEncoder;
//    }

    public static User toEntity(UserRequestDTO userRequestDTO){

        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setRoles(List.of("USER"));

        return user;
    }


    public static UserResponseDTO toDTO(User user){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId().toString());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRoles(user.getRoles());

        return userResponseDTO;
    }
}
