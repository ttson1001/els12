package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @PutMapping("{id}")
    public ResponseEntity<ResponseDTO> activeSitter(@PathVariable long sitterId){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.updateStatusSitter(1L,sitterId));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("deactive/{id}")
    public ResponseEntity<ResponseDTO> deactiveSitter(@PathVariable long sitterId){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.updateStatusSitter(2L,sitterId));
        return ResponseEntity.ok().body(responseDTO);
    }

}
