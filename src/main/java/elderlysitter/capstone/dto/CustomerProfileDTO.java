package elderlysitter.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProfileDTO {
    private String email;
    private String address;
    private LocalDate dob;
    private String fullName;
    private String gender;
    private String phone;
}
