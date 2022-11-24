package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.response.SitterCancelResponseDTO;

public interface SitterCancelService {
    SitterCancelResponseDTO cancelBooking(Long bookingId);

    SitterCancelResponseDTO changeSitter(Long bookingId);
}
