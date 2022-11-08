package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.request.AddCandidateRequestDTO;
import elderlysitter.capstone.dto.response.CandidateResponseCommonDTO;
import elderlysitter.capstone.dto.response.CandidateResponseDTO;
import elderlysitter.capstone.dto.response.CandidatesResponseDTO;

import java.util.List;

public interface CandidateService {
    CandidateResponseCommonDTO addCandidate(AddCandidateRequestDTO addCandidateRequestDTO);

    CandidateResponseCommonDTO acceptCandidate(String email);

    CandidateResponseCommonDTO rejectCandidate(String email);

    List<CandidatesResponseDTO> getAllCandidate();

    CandidateResponseDTO getById(Long id);

}
