package elderlysitter.capstone.controller;


import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAll(){
        List<User> users = userRepository.findAll();
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(users);
        return ResponseEntity.ok().body(responseDTO);
    }




}
