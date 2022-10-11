package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.CustomerRegisterDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.entities.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping()
    public ResponseEntity<ResponseDTO> CustomerRegister(@RequestBody @NotNull CustomerRegisterDTO customerRegisterDTO){
        User newUser = userService.save(User.builder()
                .fullName(customerRegisterDTO.getFullName())
                .email(customerRegisterDTO.getEmail())
                .password(passwordEncoder.encode(customerRegisterDTO.getPassword()))
                .build());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(newUser);
        return ResponseEntity.ok().body(responseDTO);
    }




}
