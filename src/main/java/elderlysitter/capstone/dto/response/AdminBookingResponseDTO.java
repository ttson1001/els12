package elderlysitter.capstone.dto.response;

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
public class AdminBookingResponseDTO {
    private String address;
    private String description;
    private String place;
    private String sitterName;
    private String customerName;
    private BigDecimal totalPrice;
    private Long totalTime;
    private List<BookingDetailResponseDTO> bookingDetailResponseDTOList;

}
