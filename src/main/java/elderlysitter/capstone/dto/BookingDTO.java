package elderlysitter.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private LocalDate startDateTime;
    private LocalDate endDateTime;
    private String place;
    private BigDecimal totalPrice;
    private Long statusId;
    private String email;
    private List<Long> serviceIds;
}
