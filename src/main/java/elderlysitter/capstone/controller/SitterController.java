package elderlysitter.capstone.controller;


import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.SitterDTO;
import elderlysitter.capstone.dto.request.UpdateSitterRequestDTO;
import elderlysitter.capstone.dto.response.SitterResponseDTO;
import elderlysitter.capstone.dto.response.SittersResponseDTO;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.services.SitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sitter")
public class SitterController {
    @Autowired
    SitterService sitterService;
    @GetMapping("sitters")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllSitter(){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
           List<SittersResponseDTO> sittersResponseDTOList = sitterService.getAllSitter();
            responseDTO.setData(sittersResponseDTOList);
            if(sittersResponseDTOList != null){
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_SITTER_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("activate/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> activateSitter(@PathVariable Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            SitterDTO sitterDTO = sitterService.activate(id);
            responseDTO.setData(sitterDTO);
            if(sitterDTO != null){
                responseDTO.setSuccessCode(SuccessCode.ACTIVATE_SITTER_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.ACTIVATE_SITTER_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.ACTIVATE_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("deactivate/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deactivateSitter(@PathVariable Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            SitterDTO sitterDTO = sitterService.deactivate(id);
            responseDTO.setData(sitterDTO);
            if(sitterDTO != null){
                responseDTO.setSuccessCode(SuccessCode.DEACTIVATE_SITTER_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.DEACTIVATE_SITTER_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.DEACTIVATE_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("get-by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getSitterById(@PathVariable Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            SitterResponseDTO sitterResponseDTO = sitterService.getSitterById(id);
            responseDTO.setData(sitterResponseDTO);
            if(sitterResponseDTO != null){
                responseDTO.setSuccessCode(SuccessCode.FIND_SITTER_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("get-by-email/{email}")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> getSitterByEmail(@PathVariable String email){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            SitterResponseDTO sitterResponseDTO = sitterService.getSitterByEmail(email);
            responseDTO.setData(sitterResponseDTO);
            if(sitterResponseDTO != null){
                responseDTO.setSuccessCode(SuccessCode.FIND_SITTER_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateSitter(@RequestBody UpdateSitterRequestDTO updateSitterRequestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            SitterDTO sitterDTO = sitterService.updateSitter(updateSitterRequestDTO);
            responseDTO.setData(sitterDTO);
            if(sitterDTO != null){
                responseDTO.setSuccessCode(SuccessCode.UPDATE_SITTER_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.UPDATE_SITTER_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.UPDATE_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }
}
