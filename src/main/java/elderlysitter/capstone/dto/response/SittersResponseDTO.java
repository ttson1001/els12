package elderlysitter.capstone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SittersResponseDTO {
    private Long id;
    private String fullName;
    private String phone;
    private String address;
    private LocalDate createDate;
    private String gender;
    private String status;
    private BigDecimal avgPrice;
}
