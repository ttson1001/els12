package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.SitterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("candidate")
public class CandidateController {
    @Autowired
    UserService userService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAll(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.findAllByRole("CANDIDATE"));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> acceptCandidate(@PathVariable String email){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.activeSitter(email));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    @PermitAll
    public  ResponseEntity<ResponseDTO> addCandidate(@RequestBody SitterDTO sitterDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.addCandidate(sitterDTO));
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getCandidateProfile(@PathVariable String email){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.getCandidateProfileByEmail(email));
        return ResponseEntity.ok().body(responseDTO);
    }

}
