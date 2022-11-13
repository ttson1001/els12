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
public class AddPaymentRequestDTO {
    private String paymentType;

    private String email;

    private BigDecimal amount;

    private Long bookingId;
}
