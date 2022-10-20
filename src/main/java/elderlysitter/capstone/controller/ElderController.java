package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.ElderService;
import elderlysitter.capstone.dto.ElderDTO;
import elderlysitter.capstone.dto.ElderProfileDTO;
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

    @GetMapping("{cusEmail}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> getAllElder(@PathVariable String cusEmail) {

        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setData(elderService.getAllElderByCustomer(cusEmail));

        }catch(Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','SITTER')")
    public ResponseEntity<ResponseDTO> getElderId(@PathVariable Long id) {

        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setData(elderService.getElderById(id));

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

    @PutMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> updateElder(@RequestBody ElderProfileDTO elderProfileDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(elderService.updateElder(elderProfileDTO));
        return ResponseEntity.ok().body(responseDTO);
    }

}
