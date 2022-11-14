package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.BookingDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.request.AddBookingImgRequestDTO;
import elderlysitter.capstone.dto.request.AddBookingRequestDTO;
import elderlysitter.capstone.dto.response.BookingResponseDTO;
import elderlysitter.capstone.dto.response.BookingsResponseDTO;
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
    public ResponseEntity<ResponseDTO> addBooking(@RequestBody AddBookingRequestDTO addBookingRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            BookingDTO bookingDTO = bookingService.addBooking(addBookingRequestDTO);
            if (addBookingRequestDTO != null) {
                responseDTO.setData(bookingDTO);
                responseDTO.setSuccessCode(SuccessCode.ADD_BOOKING_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.ADD_BOOKING_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.ADD_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("bookings")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','SITTER')")
    public ResponseEntity<ResponseDTO> getAllBooking() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<BookingsResponseDTO> bookingsResponseDTOS = bookingService.getAllBooking();
            if (bookingsResponseDTOS != null) {
                responseDTO.setData(bookingsResponseDTOS);
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_BOOKING_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("get-by-id/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','SITTER')")
    public ResponseEntity<ResponseDTO> getBookingById(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            BookingResponseDTO bookingResponseDTO = bookingService.getBookingById(id);
            if (bookingResponseDTO != null) {
                responseDTO.setData(bookingResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.FIND_BOOKING_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("bookings-by-status/{status}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','SITTER')")
    public ResponseEntity<ResponseDTO> getAllBookingByStatus(@PathVariable String status) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<BookingsResponseDTO> bookingsResponseDTOS = bookingService.getAllBookingByStatus(status);
            if (bookingsResponseDTOS != null) {
                responseDTO.setData(bookingsResponseDTOS);
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_BOOKING_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("accept/{id}")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> acceptBookingForSitter(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            BookingDTO bookingDTO = bookingService.acceptBookingForSitter(id);
            if (bookingDTO != null) {
                responseDTO.setData(bookingDTO);
                responseDTO.setSuccessCode(SuccessCode.ACCEPT_BOOKING_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.ACCEPT_BOOKING_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.ACCEPT_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("count-booking/{startDate}/{endDate}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> countBooking(@PathVariable String startDate, @PathVariable String endDate) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
                responseDTO.setData(bookingService.countBooking(startDate,endDate));
                responseDTO.setSuccessCode(SuccessCode.COUNT_BOOKING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.COUNT_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("revenue/{startDate}/{endDate}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> sumDeposit(@PathVariable String startDate, @PathVariable String endDate) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setData(bookingService.sumDeposit(startDate, endDate));
            responseDTO.setSuccessCode(SuccessCode.GET_REVENUE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.GET_REVENUE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("bookings-by-customer-email/{cusEmail}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> findAllByCustomerEmail(@PathVariable String cusEmail) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<BookingsResponseDTO> bookingsResponseDTOS = bookingService.findAllByCustomerEmail(cusEmail);
            if (bookingsResponseDTOS != null) {
                responseDTO.setData(bookingsResponseDTOS);
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_BOOKING_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("bookings-by-customer-email/{cusEmail}/{status}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> findAllByCustomerEmailAndStatus(@PathVariable String cusEmail, @PathVariable String status) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<BookingsResponseDTO> bookingsResponseDTOS = bookingService.findAllByCustomerEmailAndStatus(cusEmail,status);
            if (bookingsResponseDTOS != null) {
                responseDTO.setData(bookingsResponseDTOS);
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_BOOKING_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("bookings-by-sitter-email/{sitterEmail}")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> findAllBySitterEmail(@PathVariable String sitterEmail) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<BookingsResponseDTO> bookingsResponseDTOS = bookingService.findAllBySitterEmail(sitterEmail);
            if (bookingsResponseDTOS != null) {
                responseDTO.setData(bookingsResponseDTOS);
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_BOOKING_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("bookings-by-sitter-email/{sitterEmail}/{status}")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> findAllBySitter_EmailAndStatus(@PathVariable String sitterEmail, @PathVariable String status) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<BookingsResponseDTO> bookingsResponseDTOS = bookingService.findAllBySitter_EmailAndStatus(sitterEmail,status);
            if (bookingsResponseDTOS != null) {
                responseDTO.setData(bookingsResponseDTOS);
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_BOOKING_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("check-booking-customer/{bookingId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> checkBookingForCustomer(@PathVariable Long bookingId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            BookingsResponseDTO bookingsResponseDTO = bookingService.checkBookingForCustomer(bookingId);
            if (bookingsResponseDTO != null) {
                responseDTO.setData(bookingsResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.CHECK_BOOKING_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.CHECK_BOOKING_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.CHECK_BOOKING_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }


}
