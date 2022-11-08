package elderlysitter.capstone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidatesResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String address;
    private BigDecimal avgPrice;
}
