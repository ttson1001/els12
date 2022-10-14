package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.ElderService;
import elderlysitter.capstone.dto.ElderDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("elder")
public class ElderController {
    @Autowired
    ElderService elderService;

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> getAllElder() {

        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setData(elderService.getAllElder());

        }catch(Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> addElder(@RequestBody ElderDTO elderDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(elderService.addElder(elderDTO));
        return ResponseEntity.ok().body(responseDTO);
    }

}
