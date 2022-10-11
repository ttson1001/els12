package elderlysitter.capstone.Services;

import elderlysitter.capstone.entities.Role;
import elderlysitter.capstone.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    User save(User user);

    List<User> findAll(String roleName, String statusName);

    User updateStatusSitter(Long statusID, Long SitterID);
}
