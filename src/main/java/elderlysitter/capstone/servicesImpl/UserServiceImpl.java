package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.request.LoginGmailRequestDTO;
import elderlysitter.capstone.dto.request.AddBookingServiceRequestDTO;
import elderlysitter.capstone.entities.SitterService;
import elderlysitter.capstone.repository.RoleRepository;
import elderlysitter.capstone.services.UserService;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    @Override
    public User findByEmail(String email) {
        User user = null;
        try {
                user = userRepository.findUserByEmail(email);
        }catch (Exception e) {
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
                if (email.equalsIgnoreCase(user.getEmail())){}
                else {
                    List<SitterService> sitterServices = user.getSitterProfile().getSitterService();
                    for (SitterService sitterService : sitterServices) {
                        for (AddBookingServiceRequestDTO addbookingServiceRequestDTO : addBookingServiceRequestDTOS) {
                            if (addbookingServiceRequestDTO.getId() == sitterService.getService().getId()) count = count + 1;
                        }
                    }
                    if (count == addBookingServiceRequestDTOS.size()) {
                        return user;
                    } else {
                        count = 0;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public User loginByGmail(LoginGmailRequestDTO loginGmailRequestDTO) {
        User user= null;
        try {
            user = userRepository.findUserByEmail(loginGmailRequestDTO.getEmail());
            if(user == null){
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

        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }




}
