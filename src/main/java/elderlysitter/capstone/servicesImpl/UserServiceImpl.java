package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.EmailDTO;
import elderlysitter.capstone.dto.request.LoginGmailRequestDTO;
import elderlysitter.capstone.dto.request.AddBookingServiceRequestDTO;
import elderlysitter.capstone.entities.SitterService;
import elderlysitter.capstone.repository.RoleRepository;
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
import java.util.List;
import java.util.Random;

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
    public User randomSitter(List<AddBookingServiceRequestDTO> addBookingServiceRequestDTOS, String email) {
        try {
            List<User> users = userRepository.findAllByRole_Name("SITTER");
            int count = 0;
            for (User user : users
            ) {
                if (email.equalsIgnoreCase(user.getEmail())) {
                } else {
                    List<SitterService> sitterServices = user.getSitterProfile().getSitterService();
                    for (SitterService sitterService : sitterServices) {
                        for (AddBookingServiceRequestDTO addbookingServiceRequestDTO : addBookingServiceRequestDTOS) {
                            if (addbookingServiceRequestDTO.getId() == sitterService.getService().getId())
                                count = count + 1;
                        }
                    }
                    if (count == addBookingServiceRequestDTOS.size()) {
                        return user;
                    } else {
                        count = 0;
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
