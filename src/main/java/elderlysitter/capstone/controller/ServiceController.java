package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.ServiceService;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    @Autowired
    ServiceService serviceService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createService(@RequestBody ServiceDTO serviceDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setData(serviceService.createService(serviceDTO));
            return ResponseEntity.ok().body(responseDTO);
    }


}
