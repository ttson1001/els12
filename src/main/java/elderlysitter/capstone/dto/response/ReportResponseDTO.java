package elderlysitter.capstone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponseDTO {
    private Long id;
    private CustomerResponseDTO customerResponseDTO;
    private String comment;
}
