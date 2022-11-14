package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.request.AddBookingServiceRequestDTO;
import elderlysitter.capstone.dto.response.BookingResponseDTO;
import elderlysitter.capstone.dto.response.SitterCancelResponseDTO;
import elderlysitter.capstone.entities.*;
import elderlysitter.capstone.repository.*;
import elderlysitter.capstone.services.SitterCancelService;
import elderlysitter.capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SitterCancelServiceImpl implements SitterCancelService {
    @Autowired
    private SitterCancelRepository sitterCancelRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SitterServiceRepository sitterServiceRepository;

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

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
            User sitter = changeSitter(booking);




        }catch (Exception e){
            e.printStackTrace();
        }
        return sitterCancelResponseDTO;
    }

    private User changeSitter(Booking booking){
        User sitter = null;
        try {
            List<AddBookingServiceRequestDTO> addBookingServiceRequestDTOS = new ArrayList<>();
            List<BookingDetail> bookingDetails = booking.getBookingDetails();
            for (BookingDetail bookingDetail: bookingDetails) {
                AddBookingServiceRequestDTO addBookingServiceRequestDTO = AddBookingServiceRequestDTO.builder()
                        .id(bookingDetail.getService().getId())
                        .build();
                addBookingServiceRequestDTOS.add(addBookingServiceRequestDTO);
            }
            sitter = userService.randomSitter(addBookingServiceRequestDTOS,booking.getSitter().getEmail());
            BigDecimal total = BigDecimal.valueOf(0);
            BigDecimal deposit = BigDecimal.valueOf(0);

            if(sitter != null){
                for (BookingDetail bookingDetail: bookingDetails) {
                    SitterService sitterService = sitterServiceRepository.findBySitterProfile_User_EmailAndService_Id(sitter.getEmail(), bookingDetail.getId());
                    Long commission = sitterService.getService().getCommission();
                    deposit = deposit.add(sitterService.getPrice().multiply(BigDecimal.valueOf(bookingDetail.getDuration()).divide(BigDecimal.valueOf(60))).multiply((BigDecimal.valueOf(commission).divide(BigDecimal.valueOf(100)))));
                    total = total.add(sitterService.getPrice().multiply(BigDecimal.valueOf(bookingDetail.getDuration()).divide(BigDecimal.valueOf(60))));

                    BigDecimal price = (sitterService.getPrice().multiply(BigDecimal.valueOf(bookingDetail.getDuration()).divide(BigDecimal.valueOf(60))));

                    bookingDetail.setPrice(price.multiply(BigDecimal.valueOf(booking.getWorkingTimes().size())));
                    bookingDetailRepository.save(bookingDetail);
                }
                booking.setTotalPrice(total.multiply(BigDecimal.valueOf(booking.getWorkingTimes().size())));
                booking.setDeposit(deposit.multiply(BigDecimal.valueOf(booking.getWorkingTimes().size())));
                bookingRepository.save(booking);
            }







        }catch (Exception e){
            e.printStackTrace();
        }
        return sitter;

    }

}
