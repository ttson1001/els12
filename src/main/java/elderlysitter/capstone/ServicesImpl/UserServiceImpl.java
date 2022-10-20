package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.ChangePasswordDTO;
import elderlysitter.capstone.dto.CustomerProfileDTO;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.FavoriteSitterRepository;
import elderlysitter.capstone.repository.RoleRepository;
import elderlysitter.capstone.repository.StatusRepository;
import elderlysitter.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

}
