package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.RoleService;
import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.CustomerProfileDTO;
import elderlysitter.capstone.dto.CustomerRegisterDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.entities.FavoriteSitter;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.repository.StatusRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

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
    @PermitAll
    public ResponseEntity<ResponseDTO> customerRegister(@RequestBody @NotNull CustomerRegisterDTO customerRegisterDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        User newUser = userService.save(User.builder()
                .fullName(customerRegisterDTO.getFullName())
                .email(customerRegisterDTO.getEmail())
                .password(passwordEncoder.encode(customerRegisterDTO.getPassword()))
                        .status(statusRepository.findByStatusName("DEACTIVE"))
                        .role(roleService.findByName("CUSTOMER"))
                .build());
        if (newUser.getId() != null){
        responseDTO.setData(newUser);}
        else responseDTO.setErrorCode(ErrorCode.DUPLICATE_EMAIL);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAll(){
        List<User> users = userService.findAllByRole("CUSTOMER");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(users);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("{email}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> getAllSitter(@PathVariable String email){
        List<User> users = userService.findAllFavorite(email);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(users);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping()
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> updateProfileCus(@RequestBody CustomerProfileDTO customerProfileDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.updateCustomerProfile(customerProfileDTO));
        return ResponseEntity.ok().body(responseDTO);

    }

    @GetMapping("detail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getProfileCus(@PathVariable Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.findById(id));
        return ResponseEntity.ok().body(responseDTO);

    }






}
