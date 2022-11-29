package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.EmailDTO;
import elderlysitter.capstone.dto.request.AddBookingRequestDTO;
import elderlysitter.capstone.dto.request.AddWorkingTimesRequestDTO;
import elderlysitter.capstone.dto.request.LoginGmailRequestDTO;
import elderlysitter.capstone.dto.request.AddBookingServiceRequestDTO;
import elderlysitter.capstone.entities.SitterService;
import elderlysitter.capstone.entities.WorkingTime;
import elderlysitter.capstone.repository.RoleRepository;
import elderlysitter.capstone.repository.SitterServiceRepository;
import elderlysitter.capstone.repository.WorkingTimeRepository;
import elderlysitter.capstone.services.EmailService;
import elderlysitter.capstone.services.UserService;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.security.PermitAll;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @Autowired
    private SitterServiceRepository sitterServiceRepository;

    @Override
    public User findByEmail(String email) {
        User user = null;
        try {
            user = userRepository.findUserByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User randomSitter(AddBookingRequestDTO addBookingRequestDTO, String email) {
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
                            return sitter;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User loginByGmail(LoginGmailRequestDTO loginGmailRequestDTO) {
        User user = null;
        try {
            user = userRepository.findUserByEmail(loginGmailRequestDTO.getEmail());
            if (user == null) {
                User newUser = User.builder()
                        .email(loginGmailRequestDTO.getEmail())
                        .gender(loginGmailRequestDTO.getGender())
                        .dob(loginGmailRequestDTO.getDob())
                        .fullName(loginGmailRequestDTO.getFullName())
                        .status("ACTIVATE")
                        .role(roleRepository.findByName("CUSTOMER"))
                        .build();
                user = userRepository.save(newUser);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User forgotPassword(String email) {
        User user = null;
        String password = randomPassword() + "ELS1";
        try {
            user = userRepository.findUserByEmail(email);
            if (user == null) {
                return null;
            }
            user.setPassword(passwordEncoder.encode(password));
            EmailDTO emailDetails = EmailDTO.builder()
                    .email(email)
                    .subject("Thông báo quên mật khẩu tài khoản của email " + email)
                    .massage("Xin chào " + user.getFullName() + ",\n" +
                            "\n" +
                            "Chúng tôi xin gửi lại tài khoản và password \n" +
                            "\n" +
                            "Dưới đây là password mới của anh/chị\n" +
                            "\n" +
                            "Password: " + password + "\n" +
                            "\n" +
                            "Vui lòng đổi password ngay để tránh bị mất tài khoảng!\n" +
                            "\n" +
                            "Trân trọng,\n" +
                            "\n" +
                            "Phòng hỗ trợ khách hàng.\n" +
                            "(Đây là email được gửi tự động, Quý khách vui lòng không hồi đáp theo địa chỉ email này.)")
                    .build();

            emailService.sendSimpleMail(emailDetails);
            user = userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User logout(String email) {
        User user = null;
        try{
            user = userRepository.findUserByEmail(email);
            user.setToken(null);
            user = userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User save(User user) {
        try {
            return userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String randomPassword() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;

    }


}
