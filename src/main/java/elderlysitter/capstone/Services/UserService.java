package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.ChangePasswordDTO;
import elderlysitter.capstone.dto.CustomerProfileDTO;
import elderlysitter.capstone.entities.FavoriteSitter;
import elderlysitter.capstone.entities.Role;
import elderlysitter.capstone.entities.User;


import java.util.List;

public interface UserService {

    User findByEmail(String email);
    User save(User user);

    List<User> findAllByRole(String roleName);
    List<User> findAll(String roleName, String statusName);

    User updateStatusSitter(Long statusID, Long SitterID);

    User changePassword(ChangePasswordDTO changePasswordDTO);

    List<User> findAllFavorite(String email);

    User updateCustomerProfile(CustomerProfileDTO customerProfileDTO);
}
