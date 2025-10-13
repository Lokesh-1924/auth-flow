package mini_projects.authServer.service;

import mini_projects.authServer.dto.UserRequestDTO;
import mini_projects.authServer.dto.UserResponseDTO;
import mini_projects.authServer.entity.User;
import mini_projects.authServer.exception.DuplicateFieldException;
import mini_projects.authServer.mapper.UserMapper;
import mini_projects.authServer.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public UserResponseDTO addUser(UserRequestDTO userRequestDTO){
        if (userRepository.existsByEmail(userRequestDTO.getEmail())){
            throw new DuplicateFieldException("Account with email "+userRequestDTO.getEmail()+" already exists.");
        }
        if (userRepository.existsByUsername(userRequestDTO.getUsername())){
            throw new DuplicateFieldException("Account with username "+userRequestDTO.getUsername()+" already exists.");
        }

        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User newUser = userRepository.save(UserMapper.toEntity(userRequestDTO));
        return UserMapper.toDTO(newUser);
    }

    public List<UserResponseDTO> getUsers(){
        List<User> allUsers = userRepository.findAllActiveUsers();
        return allUsers.stream().map(UserMapper::toDTO).toList();
    }
}
