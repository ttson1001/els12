package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.ForgotPasswordDTO;
import elderlysitter.capstone.dto.request.LoginGmailRequestDTO;
import elderlysitter.capstone.services.UserService;
import elderlysitter.capstone.dto.request.LoginRequestDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.response.LoginResponseDTO;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public ResponseEntity<ResponseDTO> login(@Validated @RequestBody LoginRequestDTO user) {
        ResponseDTO responseDTO = new ResponseDTO();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        try {
            Authentication authenticate = authenticationManager.authenticate(authentication);
            if (authenticate.isAuthenticated()) {
                User userAuthenticated = userServices.findByEmail(authenticate.getName());
                String token = Jwts.builder().setSubject(authenticate.getName())
                        .claim(("authorities"), authenticate.getAuthorities()).claim("id", userAuthenticated.getId())
                        .setIssuedAt((new Date())).setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                        .signWith(jwtConfig.secretKey()).compact();

                LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                        .address(userAuthenticated.getAddress())
                        .fullName(userAuthenticated.getFullName())
                        .dob(userAuthenticated.getDob())
                        .phone(userAuthenticated.getPhone())
                        .gender(userAuthenticated.getGender())
                        .email(userAuthenticated.getEmail())
                        .role(userAuthenticated.getRole().getName())
                        .token(jwtConfig.getTokenPrefix() + token).build();

                responseDTO.setData(loginResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.LOGIN_SUCCESS);
                return ResponseEntity.ok().body(responseDTO);
            } else {
                responseDTO.setErrorCode(ErrorCode.LOGIN_FAIL);
                return ResponseEntity.ok().body(responseDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("login-gmail")
    @PermitAll
    public ResponseEntity<ResponseDTO> loginByGmail(@RequestBody LoginGmailRequestDTO loginGmailRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("CUSTOMER");
            simpleGrantedAuthorities.add(simpleGrantedAuthority);
            User user = userServices.loginByGmail(loginGmailRequestDTO);
            Authentication authenticate = new UsernamePasswordAuthenticationToken(user.getEmail(), null);
            if (user != null) {
                System.out.println(authenticate.getName());
                User userAuthenticated = userServices.findByEmail(authenticate.getName());
                String token = Jwts.builder().setSubject(authenticate.getName())
                        .claim(("authorities"), simpleGrantedAuthorities).claim("id", userAuthenticated.getId())
                        .setIssuedAt((new Date())).setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                        .signWith(jwtConfig.secretKey()).compact();

                LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                        .address(userAuthenticated.getAddress())
                        .fullName(userAuthenticated.getFullName())
                        .dob(userAuthenticated.getDob())
                        .phone(userAuthenticated.getPhone())
                        .gender(userAuthenticated.getGender())
                        .email(userAuthenticated.getEmail())
                        .role(userAuthenticated.getRole().getName())
                        .token(jwtConfig.getTokenPrefix() + token).build();

                responseDTO.setData(loginResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.LOGIN_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.LOGIN_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("forgot-password")
    @PermitAll
    public ResponseEntity<ResponseDTO> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            User user = userServices.forgotPassword(forgotPasswordDTO.getEmail());
            if (user != null) {
                responseDTO.setData(user);
                responseDTO.setSuccessCode(SuccessCode.FORGOT_PASSWORD_SUCCESS);
            }else {
                responseDTO.setErrorCode(ErrorCode.FORGOT_PASSWORD_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FORGOT_PASSWORD_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

}
