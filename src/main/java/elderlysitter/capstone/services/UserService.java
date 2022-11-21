package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.request.LoginGmailRequestDTO;
import elderlysitter.capstone.dto.request.AddBookingServiceRequestDTO;
import elderlysitter.capstone.entities.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    User randomSitter(List<AddBookingServiceRequestDTO> addBookingServiceRequestDTOS, String email);

    User loginByGmail(LoginGmailRequestDTO loginGmailRequestDTO);

    User forgotPassword(String email);


}
