package elderlysitter.capstone.dto;

import elderlysitter.capstone.entities.SitterService;
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
public class SitterResponseDTO {
// user
    private String fullName;

    private LocalDate dob;

    private String gender;

    private String phone;

    private String address;

    private String email;

    private BigDecimal avgPrice;

    // profile
    private String idNumber;

    // sitterService
    private List<SitterServiceDTO> sitterServices;
    // ratting
    private Float ratingStar;

    // certificate
    private List<CertificateDTO> certificateDTOS;
}
