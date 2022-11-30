package elderlysitter.capstone.dto.response;

import elderlysitter.capstone.dto.request.AddSitterServiceFormRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddSitterServiceFormResponseDTO {
    private Long sitterId;
    private String sitterEmail;
    private List<SitterServicesResponseDTO> sitterServicesResponseDTOS;
}
