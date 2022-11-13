package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.request.AddBookingImgRequestDTO;
import elderlysitter.capstone.dto.response.BookingImgResponseDTO;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.services.BookingImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("booking-img")
public class BookingImgController {
     @Autowired
    private BookingImgService bookingImgService;

    @PostMapping("check-in")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> checkIn(@RequestBody AddBookingImgRequestDTO addBookingImgRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            BookingImgResponseDTO bookingImgResponseDTO = bookingImgService.checkIn(addBookingImgRequestDTO);
            responseDTO.setData(bookingImgResponseDTO);
            if (bookingImgResponseDTO != null) {
                responseDTO.setSuccessCode(SuccessCode.CHECK_IN_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.CHECK_IN_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.CHECK_IN_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("check-out")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> checkOut(@RequestBody AddBookingImgRequestDTO addBookingImgRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            BookingImgResponseDTO bookingImgResponseDTO = bookingImgService.checkOut(addBookingImgRequestDTO);
            responseDTO.setData(bookingImgResponseDTO);
            if (bookingImgResponseDTO != null) {
                responseDTO.setSuccessCode(SuccessCode.CHECK_OUT_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.CHECK_OUT_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.CHECK_OUT_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }
}
