package mini_projects.authServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO<T> {
    private T data;
    private boolean success;
    private String message;

    public static <T> ApiResponseDTO<T> success(T data, String message){
        return new ApiResponseDTO<>(data, true, message);
    }

    public static <T> ApiResponseDTO<T> error(String message){
        return new ApiResponseDTO<>(null, false, message);
    }
}
