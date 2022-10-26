package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.ServiceService;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.ServiceRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("service")
public class ServiceController {

    @Autowired
    ServiceService serviceService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> createService(@RequestBody ServiceRequestDTO serviceRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setData(serviceService.createService(serviceRequestDTO));
            return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<ResponseDTO> getAllService(){
        ResponseDTO responseDTO =  new ResponseDTO();
         responseDTO.setData(serviceService.getAllService());
         return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateService(@RequestBody ServiceRequestDTO serviceRequestDTO){
        ResponseDTO responseDTO =  new ResponseDTO();
        responseDTO.setData(serviceService.updateService(serviceRequestDTO));
        return ResponseEntity.ok().body(responseDTO);
    }


}
