package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.BookingService;
import elderlysitter.capstone.Services.CandidateService;
import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.BookingServiceRequestDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.entities.Booking;
import elderlysitter.capstone.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    UserService userService;

    @Autowired
    CandidateService candidateService;

    @Autowired
    BookingService bookingService;

    @Autowired
    BookingRepository bookingRepository;



    @PostMapping
    @PermitAll
    public ResponseEntity<ResponseDTO> test(@RequestBody List<BookingServiceRequestDTO> bookingServiceRequestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.getAllSitterByBookingServiceRequestDTO(bookingServiceRequestDTO,null));
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<ResponseDTO> getAll(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(candidateService.getAllCandidate());
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("fakePayment/{bookingId}")
    @PermitAll
    public ResponseEntity<ResponseDTO> fakePayment(@PathVariable Long bookingId){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(bookingService.fakePayment(bookingId));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("checkin/{bookingId}")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> checkInBooking(@PathVariable Long bookingId){
        ResponseDTO responseDTO = new ResponseDTO();
        Booking booking = bookingRepository.findById(bookingId).get();
        booking.setStatus("STARTING");
        responseDTO.setData(bookingRepository.save(booking));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("cuscheckout/{bookingid}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> cusCheckOutBooking(@PathVariable Long bookingId){
        ResponseDTO responseDTO = new ResponseDTO();
        Booking booking = bookingRepository.findById(bookingId).get();
        booking.setIsCustomerCheckout(true);
        booking.setStatus("DONE");
        responseDTO.setData(bookingRepository.save(booking));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("sitterCheckout/{bookingid}")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> sitterCheckOutBooking(@PathVariable Long bookingId){
        ResponseDTO responseDTO = new ResponseDTO();
        Booking booking = bookingRepository.findById(bookingId).get();
        booking.setIsSitterCheckout(true);
        responseDTO.setData(bookingRepository.save(booking));
        return ResponseEntity.ok().body(responseDTO);
    }

}
