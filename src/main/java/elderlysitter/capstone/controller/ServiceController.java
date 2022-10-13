package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.ServiceService;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("service")
public class ServiceController {

    @Autowired
    ServiceService serviceService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> createService(@RequestBody ServiceDTO serviceDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setData(serviceService.createService(serviceDTO));
            return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<ResponseDTO> getAllService(){
        ResponseDTO responseDTO =  new ResponseDTO();
         responseDTO.setData(serviceService.getAllService());
         return ResponseEntity.ok().body(responseDTO);
    }


}
