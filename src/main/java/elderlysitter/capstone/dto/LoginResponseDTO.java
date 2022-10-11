package elderlysitter.capstone.dto;

import elderlysitter.capstone.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String username;
    private String password;
    private String role;
    private String token;
}
