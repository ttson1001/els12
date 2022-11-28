package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.request.AddBookingRequestDTO;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl {
    @Autowired
    UserRepository userRepository;

//    private String bookingSitter(AddBookingRequestDTO addBookingRequestDTO){
//        boolean checkCreateDate;
//        boolean checkWorkingTime;
//        boolean checkStatus;
//        List<User> sitters = userRepository.findAllByRole_Name(ad)
//
//
//
//
//        return "son";
//    }
}
