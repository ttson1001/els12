package elderlysitter.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SitterDTO {

    private String fullName;

    private LocalDate dob;

    private String gender;

    private String phone;

    private String address;

    private String email;

    private String idNumber;

    private String skill;

    private String exp;

}
