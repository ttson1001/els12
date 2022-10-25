package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.CandidateResponseDTO;
import elderlysitter.capstone.entities.User;

import java.util.List;

public interface CandidateService {
    List<CandidateResponseDTO> getAllCandidate();
    User acceptCandidate(String email);

    Boolean rejectCandidate(String email);

}
