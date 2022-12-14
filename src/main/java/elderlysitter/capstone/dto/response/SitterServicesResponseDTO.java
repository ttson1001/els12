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
public class SitterServicesResponseDTO {
    private Long id;
    private String name;
    private Long exp;
    private Integer duration;
    private BigDecimal newPrice;
    private BigDecimal price;
}
