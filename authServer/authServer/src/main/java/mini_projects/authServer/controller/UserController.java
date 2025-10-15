package mini_projects.authServer.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mini_projects.authServer.dto.ApiResponseDTO;
import mini_projects.authServer.dto.LoginRequestDTO;
import mini_projects.authServer.dto.UserRequestDTO;
import mini_projects.authServer.dto.UserResponseDTO;
import mini_projects.authServer.service.AuthService;
import mini_projects.authServer.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    public UserController(UserService userService, AuthService authService){
        this.userService = userService;
        this.authService = authService;
    }
    @PostMapping(path = "/register",consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponseDTO<?>> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO savedUser = userService.addUser(userRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(savedUser, "User Registered Successfully"));
    }

    @GetMapping(path = "/all-users")
    public ResponseEntity<ApiResponseDTO<?>> getUsers(){
        List<UserResponseDTO> allUsers = userService.getUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.success(allUsers, "Users fetched successfully"));
    }

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponseDTO<?>> login(HttpServletRequest request, @RequestBody LoginRequestDTO requestDTO){

        UserResponseDTO userResponseDTO = authService.loginUser(requestDTO, request);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponseDTO.success(userResponseDTO, "Logged in"));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<ApiResponseDTO<?>> delete(@PathVariable UUID id){
        userService.delete(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.success(null, "Deleted"));
    }
}
