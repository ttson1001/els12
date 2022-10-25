package elderlysitter.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDTO {
    private String address;
    private String description;
    private String elderId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String place;
    private String email;
    private List<String> serviceIds;
}
