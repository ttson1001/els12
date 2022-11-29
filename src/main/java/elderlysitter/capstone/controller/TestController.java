package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.MyNotificationDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.NotificationResponseDTO;
import elderlysitter.capstone.dto.request.AddBookingRequestDTO;
import elderlysitter.capstone.dto.request.AddBookingServiceRequestDTO;
import elderlysitter.capstone.dto.request.AddWorkingTimesRequestDTO;
import elderlysitter.capstone.dto.request.DateRequestDTO;
import elderlysitter.capstone.entities.Role;
import elderlysitter.capstone.entities.SitterService;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.entities.WorkingTime;
import elderlysitter.capstone.jwt.JwtConfig;
import elderlysitter.capstone.repository.*;
import elderlysitter.capstone.services.*;
import elderlysitter.capstone.servicesImpl.SitterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import javax.annotation.security.PermitAll;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    SitterServiceImpl sitterService;

    @Autowired
    SitterServiceRepository sitterServiceRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;
    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    WorkingTimeService workingTimeService;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    WorkingTimeRepository workingTimeRepository;

    @GetMapping
    @PermitAll

    public ResponseEntity<ResponseDTO> test() {
        String password = "12345678";
        ResponseDTO responseDTO = new ResponseDTO();
//        User user = userRepository.findUserByEmail("somith727@gmail.com");
        try {
            Long id = 3L;
            responseDTO.setData(bookingRepository.findById(id).get().getWorkingTimes().size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    @PermitAll
    public ResponseEntity<ResponseDTO> test1(@RequestBody AddBookingRequestDTO addBookingRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {

            List<User> sitters = userRepository.findAllByRole_NameAndStatus("SITTER", "ACTIVATE").stream()
                    .sorted(Comparator.comparing(User::getCreateDate).reversed())
                    .collect(Collectors.toList());
            List<AddBookingServiceRequestDTO> addBookingServiceRequestDTOS = addBookingRequestDTO.getAddBookingServiceRequestDTOS();
            for (User sitter : sitters) {
                if (!sitter.getEmail().equalsIgnoreCase("")) {
                    boolean checkWorkingTime = false;
                    int countWorkingTime = 0;
                    boolean checkService = false;
                    int count = 0;
                    if (sitter.getToken() != null) {
                        List<WorkingTime> workingTimes = workingTimeRepository.findAllByBooking_Sitter_IdAndStatus(sitter.getId(), "ACTIVATE");
                        List<AddWorkingTimesRequestDTO> addWorkingTimesRequestDTOs = addBookingRequestDTO.getAddWorkingTimesDTOList();
                        for (AddWorkingTimesRequestDTO addWorkingTimesRequestDTO : addWorkingTimesRequestDTOs) {
                            for (WorkingTime workingTime : workingTimes) {
                                if (addWorkingTimesRequestDTO.getStartDateTime().toLocalDate().equals(workingTime.getStartDateTime().toLocalDate())) {
                                    boolean check1 = addWorkingTimesRequestDTO.getStartDateTime().isBefore(workingTime.getStartDateTime());
                                    boolean check2 = addWorkingTimesRequestDTO.getEndDateTime().isBefore(workingTime.getStartDateTime());
                                    boolean check3 = addWorkingTimesRequestDTO.getStartDateTime().isAfter(workingTime.getStartDateTime());
                                    if (!((check1 == true && check2 == true) || check3 == true)) {
                                        countWorkingTime++;
                                    }
                                }
                            }
                        }

                        if (countWorkingTime == 0) {
                            checkWorkingTime = true;
                        }

                        List<elderlysitter.capstone.entities.SitterService> sitterServices = sitterServiceRepository.findAllBySitterProfile_User_Email(sitter.getEmail());
                        for (SitterService sitterService : sitterServices) {
                            for (AddBookingServiceRequestDTO addbookingServiceRequestDTO : addBookingServiceRequestDTOS) {
                                if (addbookingServiceRequestDTO.getId() == sitterService.getService().getId())
                                    count = count + 1;
                            }
                        }

                        if ((count == addBookingServiceRequestDTOS.size())) {
                            checkService = true;
                        }

                        if (checkService == true && checkWorkingTime == true) {
                            responseDTO.setData(sitter);
                        }
                    }
                }
            }
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

    @PostMapping("count-booking")
    @PermitAll
    public ResponseEntity<ResponseDTO> test3(@RequestBody DateRequestDTO dateRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setData(bookingService.countBooking(dateRequestDTO.getStartDate(), dateRequestDTO.getEndDate()));
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

//    @GetMapping("notification-service")
//    @PermitAll
//    public ResponseEntity<ResponseDTO> test5() {
//        ResponseDTO responseDTO = new ResponseDTO();
//        try {
//            String uri = "https://fcm.googleapis.com/fcm/send";
//            HttpHeaders httpHeaders = new HttpHeaders();
//            String reqBody = "{"to":" Rachi"}";
//
//            httpHeaders.set("Authorization","key=AAAA3DkXm68:APA91bFojmlMVxQcyjb1MAH5U_d-M6JCKa4FQzj7Z02XnZuGJ8tAIZpfMoIMSiCsJKczwXL-F8GzITTh1lM-LVPIRr9Ah4Pln1K3ZK-AoQFkV779mVu98mp_pC0ImkHDEke8-sGWQLtW");
//            RestTemplate restTemplate = new RestTemplate();
//            String reqBody = "{"city": "Ranchi"}";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.ok().body(responseDTO);
//    }

    @GetMapping(value = "/testclient")
    @PermitAll
    public ResponseEntity<ResponseDTO> testclient()
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseDTO responseDTO = new ResponseDTO();
        MyNotificationDTO myNotificationDTO = MyNotificationDTO.builder()
                .title("Khánh bịp")
                .body("Khanh dep trai")
                .build();
//        MyData data = MyData.builder()
//                .route("/home")
//                .title("Title of Your Notification in Title")
//                .key_1("Value for key_1")
//                .key_2("Value for key_2")
//                .build();

        NotificationResponseDTO testDTO = NotificationResponseDTO.builder()
                .to("d0aarqm5Qdq5s-rwYuGi9V:APA91bHQY9dvnidM2FkeL8UvVkqtnTm4BmIx4W2EAuhQInyJmSojdfz8dJqkJTxEnOPrUZrF-zlYACjfJVEA8jzuLZpG0UX9IOYcRm62fueCITb1Ir_UdZkVmBIfUr_ayuXztHykgWp_")
                .notification(myNotificationDTO)
//                .data(data)
                .build();

        responseDTO.setData(testDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "key=AAAA3DkXm68:APA91bFojmlMVxQcyjb1MAH5U_d-M6JCKa4FQzj7Z02XnZuGJ8tAIZpfMoIMSiCsJKczwXL-F8GzITTh1lM-LVPIRr9Ah4Pln1K3ZK-AoQFkV779mVu98mp_pC0ImkHDEke8-sGWQLtW");

        HttpEntity<NotificationResponseDTO> request = new HttpEntity<>(testDTO, headers);

        restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", request, NotificationResponseDTO.class);

        return ResponseEntity.ok().body(responseDTO);
    }


//    @PostMapping("Son")
//    @PermitAll
//    public ResponseEntity<ResponseDTO> test6(@RequestBody LoginRequestDTO user) {
//        ResponseDTO responseDTO = new ResponseDTO();
//        User user1 = userService.findByEmail(user.getEmail());
//        user1.
//        try {
//            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
//            Authentication authenticate = authenticationManager.authenticate(authentication);
//            User userAuthenticated = userService.findByEmail(authenticate.getName());
//            System.out.println(userAuthenticated.getEmail());
//            String token = Jwts.builder().setSubject(authenticate.getName())
//                    .claim(("authorities"), authenticate.getAuthorities()).claim("id", userAuthenticated.getId())
//                    .setIssuedAt((new Date())).setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
//                    .signWith(jwtConfig.secretKey()).compact();
//            responseDTO.setData(token);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return  ResponseEntity.ok().body(responseDTO);
//    }

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
