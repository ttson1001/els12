package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
//@SecurityRequirement(name = "els")
@RequestMapping("sitter")
public class SitterController {
    @Autowired
    UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<ResponseDTO> getAll() {
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


}
