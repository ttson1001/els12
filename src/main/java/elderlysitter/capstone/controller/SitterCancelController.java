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
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("sitter-cancel")
public class SitterCancelController {
    @Autowired
    private SitterCancelService sitterCancelService;

    @PostMapping("cancel-booking/{bookingId}")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> cancelBooking(@PathVariable Long bookingId){
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
    @GetMapping("get-total-price/{bookingId}")
    @PreAuthorize("hasRole('CUSGTOMER')")
    public ResponseEntity<ResponseDTO> getTotalPrice(@PathVariable Long bookingId){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            BigDecimal total  = sitterCancelService.getTotalPrice(bookingId);
            responseDTO.setData(total);
            if(total == BigDecimal.valueOf(0L)){
                responseDTO.setSuccessCode(SuccessCode.PAID_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.PAID_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.PAID_FAIL);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

}
