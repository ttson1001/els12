package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.SitterGetAllResponseDTO;
import elderlysitter.capstone.dto.SitterResponseDTO;

import java.util.List;

public interface SitterServiceService {
    SitterResponseDTO getSitterByEmail(String email);

    List<SitterGetAllResponseDTO> getAllSitter();
}
