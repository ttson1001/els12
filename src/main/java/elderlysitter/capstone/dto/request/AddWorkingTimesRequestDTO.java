package elderlysitter.capstone.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddWorkingTimesRequestDTO {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
