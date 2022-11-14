package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.request.AddRatingRequestDTO;
import elderlysitter.capstone.dto.response.RatingResponseDTO;

public interface RatingService {
    RatingResponseDTO rateToSitter (AddRatingRequestDTO addRatingRequestDTO);

}
