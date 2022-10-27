package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.CandidateService;
import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.BookingServiceRequestDTO;
import elderlysitter.capstone.dto.ResponseDTO;
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
}
