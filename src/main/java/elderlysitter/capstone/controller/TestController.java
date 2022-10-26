package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.BookingServiceRequestDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    UserService userService;

    @PostMapping
    @PermitAll
    public ResponseEntity<ResponseDTO> test(@RequestBody List<BookingServiceRequestDTO> bookingServiceRequestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.getAllSitterByBookingServiceRequestDTO(bookingServiceRequestDTO));
        return ResponseEntity.ok().body(responseDTO);
    }
}
