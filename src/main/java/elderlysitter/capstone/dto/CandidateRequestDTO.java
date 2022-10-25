package elderlysitter.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateRequestDTO {

    private String fullName;

    private LocalDate dob;

    private String gender;

    private String phone;

    private String address;

    private String email;

    private List<SitterServiceRequestDTO> sitterServiceRequestDTOS;

    private UserImgDTO userImgDTO;

    private String idNumber;

    private List<CertificateDTO> certificateDTOS;

}
