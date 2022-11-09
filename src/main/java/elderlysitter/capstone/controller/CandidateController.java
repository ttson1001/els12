package elderlysitter.capstone.controller;


import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.request.AddCandidateRequestDTO;
import elderlysitter.capstone.dto.response.CandidateResponseCommonDTO;
import elderlysitter.capstone.dto.response.CandidateResponseDTO;
import elderlysitter.capstone.dto.response.CandidatesResponseDTO;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.services.CandidateService;
import elderlysitter.capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequestMapping("candidate")
public class CandidateController {
    @Autowired
    CandidateService candidateService;

    @Autowired
    UserService userService;

    @PostMapping("add")
    @PermitAll
    public ResponseEntity<ResponseDTO> addCandidate(@RequestBody AddCandidateRequestDTO addCandidateRequestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            User candidate = userService.findByEmail(addCandidateRequestDTO.getEmail());
            if(candidate != null) {
                CandidateResponseCommonDTO candidateResponseCommonDTO = candidateService.addCandidate(addCandidateRequestDTO);
                responseDTO.setData(candidateResponseCommonDTO);
                if (candidateResponseCommonDTO != null) {
                    responseDTO.setSuccessCode(SuccessCode.ADD_CANDIDATE_SUCCESS);
                } else {
                    responseDTO.setErrorCode(ErrorCode.ADD_CANDIDATE_FAIL);
                }
            }else {
                responseDTO.setErrorCode(ErrorCode.DUPLICATE_EMAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.ADD_CANDIDATE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("accept/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> acceptCandidate(@PathVariable String email){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CandidateResponseCommonDTO candidateResponseCommonDTO = candidateService.acceptCandidate(email);
            responseDTO.setData(candidateResponseCommonDTO);
            if(candidateResponseCommonDTO != null){
                responseDTO.setSuccessCode(SuccessCode.ACCEPT_CANDIDATE_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.ACCEPT_CANDIDATE_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.ACCEPT_CANDIDATE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("reject/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> rejectCandidate(@PathVariable String email){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CandidateResponseCommonDTO candidateResponseCommonDTO = candidateService.rejectCandidate(email);
            responseDTO.setData(candidateResponseCommonDTO);
            if(candidateResponseCommonDTO != null){
                responseDTO.setSuccessCode(SuccessCode.REJECT_CANDIDATE_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.REJECT_CANDIDATE_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.REJECT_CANDIDATE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("candidates")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllCandidate(@PathVariable String email){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<CandidatesResponseDTO> candidatesResponseDTOList = candidateService.getAllCandidate();
            responseDTO.setData(candidatesResponseDTOList);
            if(candidatesResponseDTOList != null){
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_CANDIDATE_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_CANDIDATE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("get-by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getCandidateById(@PathVariable Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CandidateResponseDTO candidateResponseDTO = candidateService.getById(id);
            responseDTO.setData(candidateResponseDTO);
            if(candidateResponseDTO != null){
                responseDTO.setSuccessCode(SuccessCode.FIND_CANDIDATE_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_CANDIDATE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }


}
