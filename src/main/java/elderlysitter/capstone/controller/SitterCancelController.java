package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.response.SitterCancelResponseDTO;
import elderlysitter.capstone.dto.response.SitterResponseDTO;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.services.SitterCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sitter-cancel")
public class SitterCancelController {
    @Autowired
    private SitterCancelService sitterCancelService;

    @PostMapping("cancel-booking/{bookingId}")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> getAllSitter(@PathVariable Long bookingId){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            SitterCancelResponseDTO sitterCancelResponseDTO = sitterCancelService.cancelBooking(bookingId);
            responseDTO.setData(sitterCancelResponseDTO);
            if(sitterCancelResponseDTO != null){
                responseDTO.setSuccessCode(SuccessCode.SITTER_CANCEL_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.SITTER_CANCEL_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.SITTER_CANCEL_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }
}
