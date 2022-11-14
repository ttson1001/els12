package elderlysitter.capstone.dto.response;

import elderlysitter.capstone.dto.SitterDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingResponseDTO {
    private SitterDTO sitterDTO;
    private String comment;
    private Float rate;
}
