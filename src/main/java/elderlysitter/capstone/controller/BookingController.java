package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.BookingDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.request.AddBookingRequestDTO;
import elderlysitter.capstone.dto.request.ChangePasswordDTO;
import elderlysitter.capstone.dto.response.BookingResponseDTO;
import elderlysitter.capstone.dto.response.BookingsResponseDTO;
import elderlysitter.capstone.dto.response.CustomerResponseDTO;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> addBooking(@RequestBody AddBookingRequestDTO addBookingRequestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            BookingDTO bookingDTO = bookingService.addBooking(addBookingRequestDTO);
            if(addBookingRequestDTO != null){
                responseDTO.setData(bookingDTO);
                responseDTO.setSuccessCode(SuccessCode.ADD_BOOKING_SUCCESS);
            }else {
                responseDTO.setErrorCode(ErrorCode.ADD_BOOKING_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.ADD_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("bookings")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','SITTER')")
    public ResponseEntity<ResponseDTO> getAllBooking(){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<BookingsResponseDTO> bookingsResponseDTOS  = bookingService.getAllBooking();
            if(bookingsResponseDTOS != null){
                responseDTO.setData(bookingsResponseDTOS);
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_BOOKING_SUCCESS);
            }else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("get-by-id/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','SITTER')")
    public ResponseEntity<ResponseDTO> getBookingById(@PathVariable Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            BookingResponseDTO bookingResponseDTO  = bookingService.getBookingById(id);
            if(bookingResponseDTO != null){
                responseDTO.setData(bookingResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.FIND_BOOKING_SUCCESS);
            }else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("bookings-by-status/{status}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','SITTER')")
    public ResponseEntity<ResponseDTO> getAllBookingByStatus(@PathVariable String status){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<BookingsResponseDTO> bookingsResponseDTOS  = bookingService.getAllBookingByStatus(status);
            if(bookingsResponseDTOS != null){
                responseDTO.setData(bookingsResponseDTOS);
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_BOOKING_SUCCESS);
            }else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("accept/{id}")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> acceptBookingForSitter(@PathVariable Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            BookingDTO bookingDTO  = bookingService.acceptBookingForSitter(id);
            if(bookingDTO != null){
                responseDTO.setData(bookingDTO);
                responseDTO.setSuccessCode(SuccessCode.ACCEPT_BOOKING_SUCCESS);
            }else {
                responseDTO.setErrorCode(ErrorCode.ACCEPT_BOOKING_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.ACCEPT_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

}
