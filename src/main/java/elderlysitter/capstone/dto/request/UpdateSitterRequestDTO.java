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
public class UpdateSitterRequestDTO {

    private String fullName;

    private LocalDate dob;

    private String gender;

    private String phone;

    private String address;

    private String email;

    private String avatarImgUrl;
}
