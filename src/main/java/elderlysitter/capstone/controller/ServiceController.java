package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.request.AddServiceRequestDTO;
import elderlysitter.capstone.dto.request.UpdateServiceRequestDTO;
import elderlysitter.capstone.entities.Service;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequestMapping("service")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @PostMapping("add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addService(@RequestBody AddServiceRequestDTO addServiceRequestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Service service = serviceService.addService(addServiceRequestDTO);
            responseDTO.setData(service);
            if(service != null){
                responseDTO.setSuccessCode(SuccessCode.ADD_SERVICE_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.ADD_SERVICE_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.ADD_SERVICE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateService(@RequestBody UpdateServiceRequestDTO updateServiceRequestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Service service = serviceService.updateService(updateServiceRequestDTO);
            responseDTO.setData(service);
            if(service != null){
                responseDTO.setSuccessCode(SuccessCode.UPDATE_SERVICE_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.UPDATE_SERVICE_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.UPDATE_SERVICE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("activate/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> activeService(@PathVariable  Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Service service = serviceService.activeService(id);
            responseDTO.setData(service);
            if(service != null){
                responseDTO.setSuccessCode(SuccessCode.ACTIVE_SERVICE_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.ACTIVE_SERVICE_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.ACTIVE_SERVICE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("deactivate/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deactivateService(@PathVariable  Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Service service = serviceService.deactivateService(id);
            responseDTO.setData(service);
            if(service != null){
                responseDTO.setSuccessCode(SuccessCode.DEACTIVATE_SERVICE_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.DEACTIVATE_SERVICE_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.DEACTIVATE_SERVICE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping ("services")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllService(){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Service> services = serviceService.getAllService();
            responseDTO.setData(services);
            if(services != null){
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_SERVICE_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_SERVICE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping ("services-by-active")
    @PermitAll
    public ResponseEntity<ResponseDTO> getAllServiceByActive(){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Service> services = serviceService.getAllServiceByActive();
            responseDTO.setData(services);
            if(services != null){
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_SERVICE_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_SERVICE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping ("get-by-id{id}")
    @PermitAll
    public ResponseEntity<ResponseDTO> getServiceById(@PathVariable Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Service service = serviceService.getServiceById(id);
            responseDTO.setData(service);
            if(service != null){
                responseDTO.setSuccessCode(SuccessCode.FIND_SERVICE_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_SERVICE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }
}
