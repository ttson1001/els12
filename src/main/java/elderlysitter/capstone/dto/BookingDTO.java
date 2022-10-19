package elderlysitter.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDTO {
    private String name;
    private String address;
    private String description;
    private Long elderId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String place;
    private BigDecimal totalPrice;
    private Long statusId;
    private String email;
    private List<Long> serviceIds;
}
