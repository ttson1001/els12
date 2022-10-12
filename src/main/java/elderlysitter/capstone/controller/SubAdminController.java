package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.RoleService;
import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.SubAdminDTO;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("subadmin")
public class SubAdminController {

    @Autowired
    UserService userService;

    PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllSubAdmin() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setData(userService.findAll("SUBADMIN", "ACTIVE"));
            if (responseDTO.getData() != null)
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_SUCCESS);
            else responseDTO.setErrorCode(ErrorCode.FIND_ALL_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addSubAdmin(@RequestBody SubAdminDTO subAdminDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            User subAdmin = User.builder().username(subAdminDTO.getUsername())
                    .password(passwordEncoder.encode(subAdminDTO.getPassword()))
                    .role(roleService.findByName("SUBADMIN")).build();
            responseDTO.setData(userService.save(subAdmin));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(responseDTO);

    }


}
