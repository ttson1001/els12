package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.response.SitterCancelResponseDTO;

import java.math.BigDecimal;

public interface SitterCancelService {
    SitterCancelResponseDTO cancelBooking(Long bookingId);

    BigDecimal getTotalPrice(Long bookingId);
}
