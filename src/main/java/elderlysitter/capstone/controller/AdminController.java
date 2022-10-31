package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @PutMapping("active/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> activeSitter(@PathVariable Long sitterId){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.updateStatusSitter("ACTIVE",sitterId));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("deactive/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deactiveSitter(@PathVariable Long sitterId){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.updateStatusSitter("DEACTIVE",sitterId));
        return ResponseEntity.ok().body(responseDTO);
    }

}
