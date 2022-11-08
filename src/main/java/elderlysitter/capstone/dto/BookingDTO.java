package elderlysitter.capstone.dto;

import elderlysitter.capstone.dto.request.AddWorkingTimesRequestDTO;
import elderlysitter.capstone.dto.response.*;
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
public class BookingDTO {
    private String address;
    private String description;
    private ElderResponseDTO elder;
    private String place;
    private String email;
    private SitterDTO sitterDTO;
    private BigDecimal deposit;
    private CustomerResponseDTO customerResponseDTO;
    private BigDecimal totalPrice;
    private List<WorkingTimeResponseDTO> workingTimeResponseDTOList;
    private List<BookingDetailResponseDTO> bookingDetailResponseDTOList;
}
