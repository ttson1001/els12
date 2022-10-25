package elderlysitter.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDTO {
    private String address;
    private String description;
    private String elderId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String place;
    private String email;
    private BigDecimal totalPrice;
    private List<BookingServiceRequestDTO> bookingServiceRequestDTOS;
}
