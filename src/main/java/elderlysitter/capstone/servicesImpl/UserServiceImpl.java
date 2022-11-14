package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.request.AddBookingServiceRequestDTO;
import elderlysitter.capstone.entities.SitterService;
import elderlysitter.capstone.services.UserService;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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


}
