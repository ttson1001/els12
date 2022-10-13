package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.RoleService;
import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.CustomerRegisterDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.StatusRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    RoleService roleService;

    @PostMapping()
    public ResponseEntity<ResponseDTO> customerRegister(@RequestBody @NotNull CustomerRegisterDTO customerRegisterDTO){
        User newUser = userService.save(User.builder()
                .fullName(customerRegisterDTO.getFullName())
                .email(customerRegisterDTO.getEmail())
                .password(passwordEncoder.encode(customerRegisterDTO.getPassword()))
                        .status(statusRepository.findById(2L).get())
                        .role(roleService.findByName("CUSTOMER"))
                .build());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(newUser);
        return ResponseEntity.ok().body(responseDTO);
    }






}
