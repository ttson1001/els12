package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.SitterServiceService;
import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.SitterUpdateDTO;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("sitter")
public class SitterController {
    @Autowired
    UserService userService;

    @Autowired
    SitterServiceService sitterServiceService;

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

    @PutMapping
    @PreAuthorize("hasAnyRole('SITTER')")
    public ResponseEntity<ResponseDTO> updateSitter(@RequestBody SitterUpdateDTO sitterUpdateDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.updateSitter(sitterUpdateDTO));
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("getAllsitter")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ResponseDTO> getAll(){
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setData(sitterServiceService.getAllSitter());
            return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("getAllDeactiveSitter")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllCandidate() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setData(userService.findAllByRole("CANDIDATE"));
            if (responseDTO.getData() != null)
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_SUCCESS);
            else responseDTO.setErrorCode(ErrorCode.FIND_ALL_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("{email}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getSitterByEmail(@PathVariable String email){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(sitterServiceService.getSitterByEmail(email));
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("detail/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getSitterById(@PathVariable Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(sitterServiceService.getSitterById(id));
        return ResponseEntity.ok().body(responseDTO);
    }





}
