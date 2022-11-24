package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.RatingDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.request.AddRatingRequestDTO;
import elderlysitter.capstone.dto.response.RatingResponseDTO;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("rates-by-sitter-email/{email}")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> findAllBySitter_Email(@PathVariable String email) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<RatingResponseDTO> ratingResponseDTOs = ratingService.findAllBySitter_Email(email);
            responseDTO.setData(ratingResponseDTOs);
            if (ratingResponseDTOs != null) {
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("count-abilities-by-sitter-email/{email}")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> countRatingBySitter(@PathVariable String email) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            RatingDTO ratingDTO = ratingService.countRatingBySitter(email);
            responseDTO.setData(ratingDTO);
            if (ratingDTO != null) {
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }



}
