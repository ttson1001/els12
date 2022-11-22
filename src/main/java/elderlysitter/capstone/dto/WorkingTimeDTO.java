package elderlysitter.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkingTimeDTO {
    private Long bookingId;
    private LocalDateTime DateTime;
    private String status;
}
