package elderlysitter.capstone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingsResponseDTO {
    private Long id;
    private String name;
    private String sitterName;
    private String place;
    private BigDecimal totalPrice;
    private String status;
    private BigDecimal deposit;

}
