package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.SitterUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.math.BigDecimal;

@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    UserService userService;

    @GetMapping
    @PermitAll
    public ResponseEntity<ResponseDTO> test(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.getAllSitterByTotalPrice(null, BigDecimal.valueOf(Float.parseFloat("10"))));
        return ResponseEntity.ok().body(responseDTO);
    }
}
