package elderlysitter.capstone.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDetailResponseDTO {
    private Long id;
    private String serviceName;
    private Long duration;
    private BigDecimal price;
}
