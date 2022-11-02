package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.*;
import elderlysitter.capstone.entities.*;
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
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    FavoriteSitterRepository favoriteSitterRepository;

    @Autowired
    SitterServiceRepository sitterServiceRepository;


    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
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
            users = userRepository.findAllByRole_NameAndStatus(roleName, statusName);
        } catch (Exception e) {
            e.printStackTrace();
            return users;
        }
        return users;
    }


    @Override
    public User updateStatusSitter(String statusName, Long sitterId) {
        User sitter = userRepository.findById(sitterId).get();
        sitter.setStatus(statusName);
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
    public User getAllSitterByBookingServiceRequestDTO(List<BookingServiceRequestDTO> bookingServiceRequestDTOs, String email) {
        List<User> users = userRepository.findAllByRole(roleRepository.findByName("SITTER"));
        int count = 0;
        int flag = 0;
        for (User user : users
        ) {
            if (email.equalsIgnoreCase(user.getEmail())){flag = 1;}
            if(flag != 1){
                List<SitterService> sitterServices = user.getSitterProfile().getSitterService();
                for (SitterService sitterService : sitterServices) {
                    for (BookingServiceRequestDTO bookingServiceRequestDTO : bookingServiceRequestDTOs) {
                        if (bookingServiceRequestDTO.getId() == sitterService.getService().getId()) count = count + 1;
                    }
                }
                if (count == bookingServiceRequestDTOs.size()) {
                    return user;
                } else {
                    count = 0;
                }
            }
        }
        return null;
    }


}
