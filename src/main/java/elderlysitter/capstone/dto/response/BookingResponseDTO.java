package elderlysitter.capstone.dto.response;

import elderlysitter.capstone.dto.SitterDTO;
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
public class BookingResponseDTO {
    private String address;
    private String description;
    private ElderResponseDTO elder;
    private String place;
    private SitterDTO sitterDTO;
    private CustomerResponseDTO customerResponseDTO;
    private BigDecimal totalPrice;
    private BigDecimal deposit;
    private String status;
    private List<BookingImgResponseDTO> bookingImgResponseDTOList;
    private List<WorkingTimeResponseDTO> workingTimeResponseDTOList;
    private List<BookingDetailResponseDTO> bookingDetailResponseDTOList;
    private PaymentResponseDTO paymentResponseDTO;
}
