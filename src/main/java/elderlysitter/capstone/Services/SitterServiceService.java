package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.SitterResponseDTO;

public interface SitterServiceService {
    SitterResponseDTO getSitterByEmail(String email);
}
