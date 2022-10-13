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
public class ElderDTO {
    private String name;
    private Boolean gender;
    private LocalDate dob;
    private String healthStatus;
    private String note;
    private Boolean isAllergy;
    private String email;
}
