package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.StatusRepository;
import elderlysitter.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;
    @Override
    public User findByUsername(String username) {
        return  userRepository.findByUsername((username));
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
    public List<User> findAll(String roleName, String statusName) {
        List<User> users = new ArrayList<>();
        try {
            users = userRepository.findAll(roleName, statusName);
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


}
