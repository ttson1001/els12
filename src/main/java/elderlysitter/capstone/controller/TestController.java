package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.entities.Role;
import elderlysitter.capstone.entities.Service;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.BookingRepository;
import elderlysitter.capstone.repository.RoleRepository;
import elderlysitter.capstone.repository.ServiceRepository;
import elderlysitter.capstone.repository.UserRepository;
import elderlysitter.capstone.services.BookingService;
import elderlysitter.capstone.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    BookingService bookingService;

    @Autowired
    ServiceService service;

    @GetMapping
    @PermitAll

    public ResponseEntity<ResponseDTO> test() {
        String password = "12345678";
        ResponseDTO responseDTO = new ResponseDTO();
        User user = userRepository.findUserByEmail("somith727@gmail.com");
        try {
            boolean s = passwordEncoder.matches(password, user.getPassword());
            responseDTO.setData(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> test1(@RequestBody LocalDateTime start) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setData(start);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("add-full-role")
    @PermitAll
    public ResponseEntity<ResponseDTO> test2() {
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

    @GetMapping("count-booking/{startDate}/{endDate}")
    @PermitAll
    public ResponseEntity<ResponseDTO> test3(@PathVariable String startDate, @PathVariable String endDate) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setData(bookingService.countBooking(startDate, endDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("revenue/{startDate}/{endDate}")
    @PermitAll
    public ResponseEntity<ResponseDTO> test4(@PathVariable String startDate, @PathVariable String endDate) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setData(bookingService.sumDeposit(startDate, endDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("report-service")
    @PermitAll
    public ResponseEntity<ResponseDTO> test5() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setData(service.reportService());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(responseDTO);
    }

//    public static void main(String[] args) {
////        String date = "2022-11-14T00:00:00";
////
////        LocalDate date1 = LocalDateTime.parse(date).toLocalDate();
////
////        LocalDateTime localDateTime = LocalDateTime.now();
////
////        LocalDate localDate = localDateTime.toLocalDate();
////        System.out.println(date1);
////        System.out.println(localDate);
////        System.out.println(date1.isEqual(localDate));
//
////        List<String> s = new ArrayList<>();
////        s.add("s");
////        s.add("m");
////        System.out.println(s.get(s.size()-1));
//
//        System.out.println(87/9);
//
//
//
//    }



}
