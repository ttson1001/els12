package elderlysitter.capstone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SitterResponseDTO {
    private Long id;
    private String fullName;
    private String phone;
    private String address;
    private LocalDate dob;
    private String email;
    private String idNumber;
    private String description;
    private BigDecimal avgPrice;
    private String avatarUrl;
    private String frontIdImgUrl;
    private String backIdImgUrl;
    private Float ratingStart;
    private List<CertificatesResponseDTO> certificatesResponseDTOS;
    private List<SitterServicesResponseDTO> sitterServicesResponseDTOS;
}
