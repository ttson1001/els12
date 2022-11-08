package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.entities.Role;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.RoleRepository;
import elderlysitter.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("test")
@PermitAll
public class TestController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping
    @PermitAll

    public ResponseEntity<ResponseDTO> test(){
        String password = "12345678";
        ResponseDTO responseDTO = new ResponseDTO();
        User user = userRepository.findUserByEmail("somith727@gmail.com");
        try {
            boolean s  = passwordEncoder.matches(password,user.getPassword());
            responseDTO.setData(s);
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("add-full-role")
    @PermitAll
    public ResponseEntity<ResponseDTO> test1() {
        ResponseDTO responseDTO = new ResponseDTO();
        Role role = Role.builder()
                .id(1L)
                .name("ADMIN")
                .build();
        Role role1 = Role.builder().id(2L)

                .name("CUSTOMER")
                .build();
        Role role2 = Role.builder().id(3L)
                .name("CANDIDATE")
                .build();
        Role role3 = Role.builder().id(4L)
                .name("SITTER")
                .build();
        roleRepository.save(role);
        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);
        return ResponseEntity.ok().body(responseDTO);
    }
}
