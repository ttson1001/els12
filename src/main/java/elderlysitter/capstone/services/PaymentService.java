package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.request.AddPaymentRequestDTO;
import elderlysitter.capstone.dto.response.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO paid(AddPaymentRequestDTO addPaymentRequestDTO);
    PaymentResponseDTO paidBeforeChangeSitter(AddPaymentRequestDTO addPaymentRequestDTO);

}
