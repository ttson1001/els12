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
public class SitterServiceRequestDTO {
    private String Id;
    private BigDecimal servicePrice;
}
