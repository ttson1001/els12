package elderlysitter.capstone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddBookingRequestDTO {
    private String address;
    private String description;
    private Long elderId;
    private String place;
    private String email;
    private BigDecimal totalPrice;
    private List<AddWorkingTimesRequestDTO> addWorkingTimesDTOList;
    private List<AddBookingServiceRequestDTO> addBookingServiceRequestDTOS;
}
