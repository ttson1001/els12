package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.SitterDTO;
import elderlysitter.capstone.dto.request.UpdateSitterRequestDTO;
import elderlysitter.capstone.dto.response.SitterResponseDTO;
import elderlysitter.capstone.dto.response.SittersResponseDTO;

import java.util.List;

public interface SitterService {
    List<SittersResponseDTO> getAllSitter();

    SitterDTO activate (Long id);

    SitterDTO deactivate(Long id);

    SitterResponseDTO getSitterById(Long id);

    SitterResponseDTO getSitterByEmail(String email);

    SitterDTO updateSitter (UpdateSitterRequestDTO updateSitterRequestDTO);


}
