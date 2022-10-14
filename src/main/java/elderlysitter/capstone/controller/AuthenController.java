package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.ChangePasswordDTO;
import elderlysitter.capstone.dto.LoginDTO;
import elderlysitter.capstone.dto.LoginResponseDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.jwt.JwtConfig;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("auth")
public class AuthenController {
    private AuthenticationManager authenticationManager;
    private UserService userServices;
    private JwtConfig jwtConfig;
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    public AuthenController(AuthenticationManager authenticationManager, UserService userService, JwtConfig jwtConfig, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userServices = userService;
        this.jwtConfig = jwtConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping()
    @PermitAll
    public ResponseEntity<ResponseDTO> login(@Validated @RequestBody LoginDTO user){
        ResponseDTO responseDTO = new ResponseDTO();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        try{
            Authentication authenticate = authenticationManager.authenticate(authentication);
            if(authenticate.isAuthenticated()){
                User userAuthenticated = userServices.findByEmail(authenticate.getName());
                String token = Jwts.builder().setSubject(authenticate.getName())
                        .claim(("authorities"), authenticate.getAuthorities()).claim("id",userAuthenticated.getId())
                        .setIssuedAt((new Date())).setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                        .signWith(jwtConfig.secretKey()).compact();

                LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder().username(userAuthenticated.getEmail()).role(userAuthenticated.getRole().getName())
                        .token(jwtConfig.getTokenPrefix() + token).build();

                responseDTO.setData(loginResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.LOGIN_SUCCESS);
                return ResponseEntity.ok().body(responseDTO);
            }else {
                responseDTO.setErrorCode(ErrorCode.LOGIN_FAIL);
                return ResponseEntity.ok().body(responseDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping()
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userServices.changePassword(changePasswordDTO));
        return ResponseEntity.ok().body(responseDTO);


    }
}
