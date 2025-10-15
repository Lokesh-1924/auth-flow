package mini_projects.authServer.dto;

import jakarta.persistence.SqlResultSetMappings;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "Username is required")
    @Size(max = 100, message = "Username cannot exceed 100 character")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
