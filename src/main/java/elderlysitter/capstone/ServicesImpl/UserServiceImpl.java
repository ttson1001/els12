package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.EmailService;
import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.*;
import elderlysitter.capstone.entities.SitterProfile;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    FavoriteSitterRepository favoriteSitterRepository;

    @Autowired
    SitterProfileRepository sitterProfileRepository;

    @Autowired
    EmailService emailService;

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User save(User user) {
        User saveUser = new User();
        try {
            saveUser = userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
            return saveUser;
        }
        return saveUser;
    }

    @Override
    public List<User> findAllByRole(String roleName) {
        List<User> users = new ArrayList<>();
        try {
            users = userRepository.findAllByRole(roleRepository.findByName(roleName));
        }catch (Exception e){
            e.printStackTrace();
            return users;
        }
        return users;
    }

    @Override
    public List<User> findAll(String roleName, String statusName) {
        List<User> users = new ArrayList<>();
        try {
            users = userRepository.findAllByRole_NameAndStatus_StatusName(roleName, statusName);
        }catch (Exception e){
            e.printStackTrace();
            return users;
        }
        return users;
    }



    @Override
    public User updateStatusSitter(Long statusID, Long sitterId) {
        User sitter = userRepository.findById(sitterId).get();
        sitter.setStatus(statusRepository.findById(statusID).get());
        return userRepository.save(sitter);
    }

    @Override
    public User changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = userRepository.findUserByEmail(changePasswordDTO.getEmail());
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllFavorite(String email) {
        List<User> users = favoriteSitterRepository.findAll(email);
        return users;
    }

    @Override
    public User updateCustomerProfile(CustomerProfileDTO customerProfileDTO) {
        User oldUser = userRepository.findUserByEmail(customerProfileDTO.getEmail());
        oldUser.setFullName(customerProfileDTO.getFullName());
        oldUser.setDob(customerProfileDTO.getDob());
        oldUser.setAddress(customerProfileDTO.getAddress());
        oldUser.setGender(customerProfileDTO.getGender());
        oldUser.setPhone(customerProfileDTO.getPhone());
        return userRepository.save(oldUser);
    }

    @Override
    public User addSitter(SitterDTO sitterDTO) {
        User sitter = User.builder()
                .role(roleRepository.findByName("SITTER"))
                .fullName(sitterDTO.getFullName())
                .status(statusRepository.findByStatusName("DEACTIVE"))
                .dob(sitterDTO.getDob())
                .address(sitterDTO.getAddress())
                .gender(sitterDTO.getGender())
                .phone(sitterDTO.getPhone())
                .email(sitterDTO.getEmail()).build();
        User newSitter = userRepository.save(sitter);
        SitterProfile sitterProfile = SitterProfile.builder()
                .user(newSitter)
                .exp(sitterDTO.getExp())
                .skill(sitterDTO.getSkill())
                .idNumber(sitterDTO.getIdNumber())
                .build();
        sitterProfileRepository.save(sitterProfile);
        return  newSitter;
    }

    @Override
    public User activeSitter(String email) {
        System.out.println(email);
        String password = randomPassword()+"ELS1";
        User  sitter = userRepository.findUserByEmail(email);
        if(sitter == null) {
            return null;
        }

        sitter.setStatus(statusRepository.findByStatusName("ACTIVE"));
        sitter.setPassword(passwordEncoder.encode(password));
        EmailDTO emailDetails = EmailDTO.builder()
                .email(email)
                .subject("Your password from els ")
                .massage(password)
                .build();
        emailService.sendSimpleMail(emailDetails);
        return userRepository.save(sitter);
    }

    @Override
    public User updateSitter(SitterUpdateDTO sitterUpdateDTO) {
        User sitter = userRepository.findUserByEmail(sitterUpdateDTO.getEmail());
            sitter.setDob(sitterUpdateDTO.getDob());
            sitter.setAddress(sitterUpdateDTO.getAddress());
            sitter.setFullName(sitterUpdateDTO.getFullName());
            sitter.setGender(sitterUpdateDTO.getGender());
            sitter.setPhone(sitterUpdateDTO.getPhone());
            return  userRepository.save(sitter);
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
