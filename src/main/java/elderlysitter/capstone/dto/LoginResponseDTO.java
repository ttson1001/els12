package elderlysitter.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String email;
    private String password;
    private String role;
    private String token;
    private String address;
    private LocalDate dob;
    private String fullName;
    private String gender;
    private String phone;
}
