package elderlysitter.capstone.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDTO {

    private  Long id;

    private String fullName;

    private LocalDate dob;

    private String gender;

    private String phone;

    private String address;

    private String email;

    private String frontIdImgUrl;

    private String backIdImgUrl;

    private String avatarImgUrl;

    private String status;


}
