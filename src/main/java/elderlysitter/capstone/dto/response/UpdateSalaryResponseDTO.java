package elderlysitter.capstone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateSalaryResponseDTO {
    private Long sitterId;
    private String sitterEmail;
    List<SitterServicesResponseDTO> sitterServicesResponseDTOS;
}
