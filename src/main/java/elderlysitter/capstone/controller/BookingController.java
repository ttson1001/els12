package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.BookingService;
import elderlysitter.capstone.dto.BookingDTO;
import elderlysitter.capstone.dto.BookingSitterDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping()
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> addBooking(@RequestBody BookingDTO bookingDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(bookingService.addBookingService(bookingDTO));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("addSitter")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> bookSitter(@RequestBody BookingSitterDTO bookingSitterDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(bookingService.bookingSitter(bookingSitterDTO));
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("{statusId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getBookingByStatus(@PathVariable Long statusId){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(bookingService.getListBookingBYStatus(statusId));
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllBooking(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(bookingService.getAllBooking());
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("customer/{cusEmail}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public  ResponseEntity<ResponseDTO> getALlBookingByCustomerID(@PathVariable String cusEmail){
        ResponseDTO responseDTO = new ResponseDTO();
        try {

            responseDTO.setData(bookingService.getAllBookingByCustomerEmail(cusEmail));
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(responseDTO);
    }

}
