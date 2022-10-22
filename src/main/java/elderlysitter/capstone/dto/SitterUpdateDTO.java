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
public class SitterUpdateDTO {
    private String fullName;

    private LocalDate dob;

    private String gender;

    private String phone;

    private String address;

    private String email;
}
