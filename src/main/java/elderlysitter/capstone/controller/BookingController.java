package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.BookingService;
import elderlysitter.capstone.dto.BookingDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping()
    public ResponseEntity<ResponseDTO> addBooking(@RequestBody BookingDTO bookingDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(bookingDTO);
        return  ResponseEntity.ok().body(responseDTO);
    }


}
