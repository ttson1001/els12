package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.CandidateRequestDTO;
import elderlysitter.capstone.dto.CandidateResponseDTO;
import elderlysitter.capstone.entities.SitterProfile;
import elderlysitter.capstone.entities.User;

import java.util.List;

public interface CandidateService {
    List<CandidateResponseDTO> getAllCandidate();
    User acceptCandidate(String email);

    Boolean rejectCandidate(String email);

    SitterProfile getCandidateProfileByEmail(String email);

    User addCandidate(CandidateRequestDTO candidateRequestDTO);
}
