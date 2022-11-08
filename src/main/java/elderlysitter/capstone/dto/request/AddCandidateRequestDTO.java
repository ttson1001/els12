package elderlysitter.capstone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCandidateRequestDTO {

    private String fullName;

    private LocalDate dob;

    private String gender;

    private String phone;

    private String address;

    private String frontIdImgUrl;

    private String backIdImgUrl;

    private String avatarImgUrl;

    private String email;

    private List<AddSitterServiceRequestDTO> addSitterServiceRequestDTOList;

    private String idNumber;

    private String description;

    private List<AddCertificateRequestDTO> addCertificateRequestDTOS;

}
