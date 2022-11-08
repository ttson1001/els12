package elderlysitter.capstone.dto.request;

import lombok.*;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO {
    @NonNull
    private String email;
    @NonNull
    @Size(min= 8)
    private String password;
}
