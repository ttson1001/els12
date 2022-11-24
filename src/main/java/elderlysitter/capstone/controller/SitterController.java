package elderlysitter.capstone.controller;


import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.SitterDTO;
import elderlysitter.capstone.dto.request.ChangePasswordDTO;
import elderlysitter.capstone.dto.request.UpdateSalaryRequestDTO;
import elderlysitter.capstone.dto.request.UpdateSitterRequestDTO;
import elderlysitter.capstone.dto.response.SitterResponseDTO;
import elderlysitter.capstone.dto.response.SittersResponseDTO;
import elderlysitter.capstone.dto.response.UpdateSalaryResponseDTO;
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
    public ResponseEntity<ResponseDTO> getAllSitter() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<SittersResponseDTO> sittersResponseDTOList = sitterService.getAllSitter();
            responseDTO.setData(sittersResponseDTOList);
            if (sittersResponseDTOList != null) {
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_SITTER_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("activate/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> activateSitter(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            SitterDTO sitterDTO = sitterService.activate(id);
            responseDTO.setData(sitterDTO);
            if (sitterDTO != null) {
                responseDTO.setSuccessCode(SuccessCode.ACTIVATE_SITTER_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.ACTIVATE_SITTER_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.ACTIVATE_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("deactivate/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deactivateSitter(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            SitterDTO sitterDTO = sitterService.deactivate(id);
            responseDTO.setData(sitterDTO);
            if (sitterDTO != null) {
                responseDTO.setSuccessCode(SuccessCode.DEACTIVATE_SITTER_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.DEACTIVATE_SITTER_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.DEACTIVATE_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("get-by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getSitterById(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            SitterResponseDTO sitterResponseDTO = sitterService.getSitterById(id);
            responseDTO.setData(sitterResponseDTO);
            if (sitterResponseDTO != null) {
                responseDTO.setSuccessCode(SuccessCode.FIND_SITTER_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("get-by-email/{email}")
    @PreAuthorize("hasAnyRole('SITTER','CUSTOMER')")
    public ResponseEntity<ResponseDTO> getSitterByEmail(@PathVariable String email) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            SitterResponseDTO sitterResponseDTO = sitterService.getSitterByEmail(email);
            responseDTO.setData(sitterResponseDTO);
            if (sitterResponseDTO != null) {
                responseDTO.setSuccessCode(SuccessCode.FIND_SITTER_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("update")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> updateSitter(@RequestBody UpdateSitterRequestDTO updateSitterRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            SitterDTO sitterDTO = sitterService.updateSitter(updateSitterRequestDTO);
            responseDTO.setData(sitterDTO);
            if (sitterDTO != null) {
                responseDTO.setSuccessCode(SuccessCode.UPDATE_SITTER_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.UPDATE_SITTER_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.UPDATE_SITTER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("change-password")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            SitterDTO sitterDTO = sitterService.changePassword(changePasswordDTO);
            if (sitterDTO != null) {
                responseDTO.setData(sitterDTO);
                responseDTO.setSuccessCode(SuccessCode.CHANGE_PASSWORD_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.CHANGE_PASSWORD_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.CHANGE_PASSWORD_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("up-salary")
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> upSalary(@RequestBody UpdateSalaryRequestDTO updateSalaryRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            UpdateSalaryResponseDTO updateSalaryResponseDTO = sitterService.updateSalary(updateSalaryRequestDTO);
            if (updateSalaryResponseDTO != null) {
                responseDTO.setData(updateSalaryResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.UP_SALARY_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.UP_SALARY_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.UP_SALARY_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("up-salary-forms")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllFormUpdateSalary() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<UpdateSalaryResponseDTO> updateSalaryResponseDTOs = sitterService.getAllFormUpdateSalary();
            if (updateSalaryResponseDTOs != null) {
                responseDTO.setData(updateSalaryResponseDTOs);
                responseDTO.setSuccessCode(SuccessCode.GET_FORMS_SALARY_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.GET_FORMS_SALARY_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.GET_FORMS_SALARY_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("up-salary-form/{sitterId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getFormUpdateSalaryById(@PathVariable Long sitterId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            UpdateSalaryResponseDTO updateSalaryResponseDTO = sitterService.getFormBySitterId(sitterId);
            if (updateSalaryResponseDTO != null) {
                responseDTO.setData(updateSalaryResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.GET_FORMS_SALARY_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.GET_FORMS_SALARY_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.GET_FORMS_SALARY_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("approve-up-salary/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> approveUpSalary(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            UpdateSalaryResponseDTO updateSalaryResponseDTO = sitterService.approveUpSalary(id);
            if (updateSalaryResponseDTO != null) {
                responseDTO.setData(updateSalaryResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.APPROVE_UP_SALARY_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.APPROVE_UP_SALARY_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.APPROVE_UP_SALARY_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("reject-up-salary/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> rejectUpSalary(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            UpdateSalaryResponseDTO updateSalaryResponseDTO = sitterService.rejectUpSalary(id);
            if (updateSalaryResponseDTO != null) {
                responseDTO.setData(updateSalaryResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.REJECT_UP_SALARY_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.REJECT_APPROVE_UP_SALARY_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.REJECT_APPROVE_UP_SALARY_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }


}
