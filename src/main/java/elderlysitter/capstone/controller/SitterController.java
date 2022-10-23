package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.SitterDTO;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;


@RestController
@RequestMapping("sitter")
public class SitterController {
    @Autowired
    UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<ResponseDTO> getAllActive() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setData(userService.findAll("SITTER", "ACTIVE"));
            if (responseDTO.getData() != null)
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_SUCCESS);
            else responseDTO.setErrorCode(ErrorCode.FIND_ALL_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    @PermitAll
    public  ResponseEntity<ResponseDTO> addSitter(@RequestBody SitterDTO sitterDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.addSitter(sitterDTO));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ResponseDTO> acceptSitter(@PathVariable String email){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.activeSitter(email));
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("getAllsitter")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ResponseDTO> getAll(){
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setData(userService.findAllByRole("SITTER"));
            return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("getAllDeactiveSitter")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllDeactive() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setData(userService.findAll("SITTER", "DEACTIVE"));
            if (responseDTO.getData() != null)
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_SUCCESS);
            else responseDTO.setErrorCode(ErrorCode.FIND_ALL_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }



}
