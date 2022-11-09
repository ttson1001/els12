package elderlysitter.capstone.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCustomerRequestDTO {

    private String password;

    private String fullName;

    private String email;
}
