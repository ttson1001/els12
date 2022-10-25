package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.EmailService;
import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.*;
import elderlysitter.capstone.entities.CertificateSitter;
import elderlysitter.capstone.entities.SitterProfile;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.entities.UserImg;
import elderlysitter.capstone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

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

    @Autowired
    CertificateSitterRepository certificateSitterRepository;

    @Autowired
    UserImgRepository userImgRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User save(User user) {
        User saveUser = new User();
        try {
            saveUser = userRepository.save(user);
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
    public User addCandidate(CandidateRequestDTO candidateRequestDTO) {
        User candidate = User.builder()
                .role(roleRepository.findByName("CANDIDATE"))
                .fullName(candidateRequestDTO.getFullName())
                .dob(candidateRequestDTO.getDob())
                .address(candidateRequestDTO.getAddress())
                .gender(candidateRequestDTO.getGender())
                .phone(candidateRequestDTO.getPhone())
                .email(candidateRequestDTO.getEmail()).build();
        User newSitter = userRepository.save(candidate);
        SitterProfile sitterProfile = SitterProfile.builder()
                .user(newSitter)
                .exp(candidateRequestDTO.getExp())
                .skill(candidateRequestDTO.getSkill())
                .idNumber(candidateRequestDTO.getIdNumber())
                .build();
        SitterProfile sitterProfile1 = sitterProfileRepository.save(sitterProfile);

        List<CertificateDTO> list = candidateRequestDTO.getCertificateDTOS();

        for (int i = 0; i < list.size(); i++) {
            CertificateSitter certificateSitter = CertificateSitter.builder()
                    .sitterProfile(sitterProfile1)
                    .url(list.get(i).getUrl())
                    .exp(list.get(i).getExp())
                    .build();
            certificateSitterRepository.save(certificateSitter);
        }

        UserImg userImg = UserImg.builder()
                .avatarImgUrl(candidateRequestDTO.getUserImgDTO().getAvatarImgUrl())
                .backIdImgUrl(candidateRequestDTO.getUserImgDTO().getBackIdImgUrl())
                .fontIdImgUrl(candidateRequestDTO.getUserImgDTO().getFontIdImgUrl())
                .build();

        userImgRepository.save(userImg);

        return newSitter;
    }



    @Override
    public User updateSitter(SitterUpdateDTO sitterUpdateDTO) {
        User sitter = userRepository.findUserByEmail(sitterUpdateDTO.getEmail());
        sitter.setDob(sitterUpdateDTO.getDob());
        sitter.setAddress(sitterUpdateDTO.getAddress());
        sitter.setFullName(sitterUpdateDTO.getFullName());
        sitter.setGender(sitterUpdateDTO.getGender());
        sitter.setPhone(sitterUpdateDTO.getPhone());
        return userRepository.save(sitter);
    }

    @Override
    public SitterProfile getCandidateProfileByEmail(String email) {
        return sitterProfileRepository.findByUser_Email(email);
    }


}
