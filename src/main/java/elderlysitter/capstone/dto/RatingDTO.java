package elderlysitter.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingDTO {
    private Long sitterId;
    private String email;
    private Long numberOfOnTime;
    private Long numberOfDiligent;
    private Long numberOfEnthusiasm;
}
