package elderlysitter.capstone.controller;

import elderlysitter.capstone.services.ElderService;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.request.AddElderRequestDTO;
import elderlysitter.capstone.dto.request.UpdateElderRequestDTO;
import elderlysitter.capstone.entities.Elder;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("elder")
public class ElderController {

    @Autowired
    private ElderService elderService;

    @PostMapping("add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> addElder(@RequestBody AddElderRequestDTO addElderRequestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Elder elder = elderService.addElder(addElderRequestDTO);
            responseDTO.setData(elder);
            if(elder != null){
                responseDTO.setSuccessCode(SuccessCode.ADD_ELDER_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.ADD_ELDER_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.ADD_ELDER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("update")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> updateElder(@RequestBody UpdateElderRequestDTO updateElderRequestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Elder elder = elderService.updateElder(updateElderRequestDTO);
            responseDTO.setData(elder);
            if(elder != null){
                responseDTO.setSuccessCode(SuccessCode.UPDATE_ELDER_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.UPDATE_ELDER_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.UPDATE_ELDER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("remove/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> removeElder(@PathVariable Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Elder elder = elderService.removeElder(id);
            responseDTO.setData(elder);
            if(elder != null){
                responseDTO.setSuccessCode(SuccessCode.REMOVE_ELDER_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.REMOVE_ELDER_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.REMOVE_ELDER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("elders-by-customer/{email}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> getAllElderByCustomerEmail(@PathVariable String email){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Elder> elders = elderService.getAllElderByCustomerEmail(email);
            responseDTO.setData(elders);
            if(elders != null){
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_ELDER_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.ELDER_NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_ELDER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("get-by-id/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> getElderById(@PathVariable Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Elder elder = elderService.getElderById(id);
            responseDTO.setData(elder);
            if(elder != null){
                responseDTO.setSuccessCode(SuccessCode.FIND_ELDER_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.ELDER_NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ELDER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

}
