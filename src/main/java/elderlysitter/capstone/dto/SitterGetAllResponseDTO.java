package elderlysitter.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SitterGetAllResponseDTO {
    private Long id;

    private String fullName;

    private LocalDate dob;

    private String gender;

    private String address;

    private String email;

    private BigDecimal avgPrice;

    private LocalDate crateDate;

    // ratting
    private Float ratingStar;

}
