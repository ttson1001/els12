package elderlysitter.capstone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomersResponseDTO {
    private  Long id;

    private String fullName;

    private String gender;

    private String phone;

    private String email;

    private String status;

    private String address;

}
