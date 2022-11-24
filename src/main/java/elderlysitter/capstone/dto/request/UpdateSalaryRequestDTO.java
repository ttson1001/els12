package elderlysitter.capstone.dto.request;

import elderlysitter.capstone.dto.UpdateSalaryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateSalaryRequestDTO {
    private String sitterEmail;
    private List<UpdateSalaryDTO> updateSalaryDTOS;
}
