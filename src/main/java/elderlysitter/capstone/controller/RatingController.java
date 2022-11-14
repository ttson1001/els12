package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.request.AddRatingRequestDTO;
import elderlysitter.capstone.dto.response.RatingResponseDTO;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping("rate")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> paid(@RequestBody AddRatingRequestDTO addRatingRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            RatingResponseDTO ratingResponseDTO = ratingService.rateToSitter(addRatingRequestDTO);
            responseDTO.setData(ratingResponseDTO);
            if (ratingResponseDTO != null) {
                responseDTO.setSuccessCode(SuccessCode.RATE_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.RATE_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.RATE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }
}
