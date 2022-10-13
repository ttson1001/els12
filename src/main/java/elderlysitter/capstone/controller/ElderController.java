package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.ElderService;
import elderlysitter.capstone.dto.ElderDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("elder")
public class ElderController {
    @Autowired
    ElderService elderService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllElder() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(elderService.getAllElder());
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addElder(@RequestBody ElderDTO elderDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(elderService.addElder(elderDTO));
        return ResponseEntity.ok().body(responseDTO);
    }

}
