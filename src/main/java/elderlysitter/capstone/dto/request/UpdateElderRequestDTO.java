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
public class UpdateElderRequestDTO {
    private Long id;

    private String name;

    private String gender;

    private LocalDate dob;

    private String healthStatus;

    private String note;

    private Boolean isAllergy;
}
