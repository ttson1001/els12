package elderlysitter.capstone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddSitterServiceFormRequestDTO {
    private String email;
    private Long serviceId;
    private BigDecimal price;
    private Long exp;
}
