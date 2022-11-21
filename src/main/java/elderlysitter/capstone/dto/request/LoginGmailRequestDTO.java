package elderlysitter.capstone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginGmailRequestDTO {
    private String email;
    private String fullName;
    private LocalDate dob;
    private String gender;
}
