package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.RatingDTO;
import elderlysitter.capstone.dto.request.AddRatingRequestDTO;
import elderlysitter.capstone.dto.response.RatingResponseDTO;

import java.util.List;

public interface RatingService {
    RatingResponseDTO rateToSitter (AddRatingRequestDTO addRatingRequestDTO);

    List<RatingResponseDTO>  findAllBySitter_Email(String email);

    RatingDTO countRatingBySitter(String email);

}
