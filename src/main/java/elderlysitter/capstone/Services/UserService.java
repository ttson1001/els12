package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.*;
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

    User getAllSitterByBookingServiceRequestDTO(List<BookingServiceRequestDTO> bookingServiceRequestDTO, String email);

    User updateSitter(SitterUpdateDTO sitterUpdateDTO);


}
