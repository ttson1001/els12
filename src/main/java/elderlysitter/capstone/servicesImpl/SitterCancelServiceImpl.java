package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.response.BookingResponseDTO;
import elderlysitter.capstone.dto.response.SitterCancelResponseDTO;
import elderlysitter.capstone.entities.Booking;
import elderlysitter.capstone.entities.SitterCancel;
import elderlysitter.capstone.repository.BookingRepository;
import elderlysitter.capstone.repository.SitterCancelRepository;
import elderlysitter.capstone.repository.UserRepository;
import elderlysitter.capstone.services.SitterCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SitterCancelServiceImpl implements SitterCancelService {
    @Autowired
    private SitterCancelRepository sitterCancelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public SitterCancelResponseDTO cancelBooking(Long bookingId) {
        SitterCancelResponseDTO sitterCancelResponseDTO = null;
        try {
            Booking booking = bookingRepository.findById(bookingId).get();
            SitterCancel sitterCancel = SitterCancel.builder()
                    .user(booking.getSitter())
                    .booking(booking)
                    .build();
            sitterCancel = sitterCancelRepository.save(sitterCancel);
            sitterCancelResponseDTO = SitterCancelResponseDTO.builder()
                    .bookingName(sitterCancel.getBooking().getName())
                    .sitterName(sitterCancel.getUser().getFullName())
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sitterCancelResponseDTO;
    }
}
