package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.request.AddBookingRequestDTO;
import elderlysitter.capstone.dto.request.AddBookingServiceRequestDTO;
import elderlysitter.capstone.dto.request.AddWorkingTimesRequestDTO;
import elderlysitter.capstone.dto.response.SitterCancelResponseDTO;
import elderlysitter.capstone.entities.*;
import elderlysitter.capstone.enumCode.StatusCode;
import elderlysitter.capstone.repository.*;
import elderlysitter.capstone.services.NotificationService;
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

    @Autowired
    private NotificationService notificationService;

    @Override
    public SitterCancelResponseDTO cancelBooking(Long bookingId) {
        SitterCancelResponseDTO sitterCancelResponseDTO = null;
        try {
            Booking booking = bookingRepository.findById(bookingId).get();
            SitterCancel sitterCancel = SitterCancel.builder()
                    .user(booking.getSitter())
                    .booking(booking)
                    .status(StatusCode.CANCEL.toString())
                    .build();
            sitterCancel = sitterCancelRepository.save(sitterCancel);
            sitterCancelResponseDTO = SitterCancelResponseDTO.builder()
                    .bookingName(sitterCancel.getBooking().getName())
                    .sitterName(sitterCancel.getUser().getFullName())
                    .build();
            notificationService.sendNotification(booking.getUser().getId(),  "Sitter không chấp nhận đơn hàng của bạn\n Chúng tôi sẽ tìm sitter mới cho bạn","");
            changeSitter(booking);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sitterCancelResponseDTO;
    }

    @Override
    public BigDecimal getTotalPrice(Long bookingId) {
        BigDecimal total  = BigDecimal.valueOf(0);
        try {
            Booking booking = bookingRepository.findById(bookingId).get();
            List<BookingDetail> bookingDetails = booking.getBookingDetails();
            for(BookingDetail bookingDetail: bookingDetails){
                SitterService sitterService = sitterServiceRepository.findBySitterProfile_User_EmailAndService_Id(booking.getSitter().getEmail(), bookingDetail.getId());
                total = total.add(sitterService.getPrice().multiply(BigDecimal.valueOf(bookingDetail.getDuration()).divide(BigDecimal.valueOf(sitterService.getService().getDuration()))));
            }
            Long count = 0L;
            List<WorkingTime> workingTimes = booking.getWorkingTimes();
            for (WorkingTime workingTime: workingTimes
                 ) {
                if(workingTime.getStatus().equalsIgnoreCase(StatusCode.DONE.toString())){
                    count = count + 1;
                }
            }
            total = total.multiply(BigDecimal.valueOf(count));

        }catch (Exception e){
            e.printStackTrace();
        }
        return  total;

    }

    private User changeSitter(Booking booking){

        User sitter = null;
        try {
            List<AddBookingServiceRequestDTO> addBookingServiceRequestDTOS = new ArrayList<>();
            List<AddWorkingTimesRequestDTO> addWorkingTimesRequestDTOS = new ArrayList<>();
            List<BookingDetail> bookingDetails = booking.getBookingDetails();
            for (BookingDetail bookingDetail: bookingDetails) {
                AddBookingServiceRequestDTO addBookingServiceRequestDTO = AddBookingServiceRequestDTO.builder()
                        .id(bookingDetail.getService().getId())
                        .build();
                addBookingServiceRequestDTOS.add(addBookingServiceRequestDTO);
            }
            List<WorkingTime> workingTimes = booking.getWorkingTimes();
            for (WorkingTime workingTime: workingTimes) {
                AddWorkingTimesRequestDTO addWorkingTimesRequestDTO = AddWorkingTimesRequestDTO.builder()
                        .startDateTime(workingTime.getStartDateTime())
                        .endDateTime(workingTime.getEndDateTime())
                        .build();
                addWorkingTimesRequestDTOS.add(addWorkingTimesRequestDTO);
            }
            AddBookingRequestDTO addBookingRequestDTO = AddBookingRequestDTO.builder()
                    .addBookingServiceRequestDTOS(addBookingServiceRequestDTOS)
                    .addWorkingTimesDTOList(addWorkingTimesRequestDTOS)
                    .build();
            sitter = userService.randomSitter(addBookingRequestDTO,booking.getSitter().getEmail());
            BigDecimal total = BigDecimal.valueOf(0);
            BigDecimal deposit = BigDecimal.valueOf(0);

            if(sitter != null){
                for (BookingDetail bookingDetail: bookingDetails) {
                    SitterService sitterService = sitterServiceRepository.findBySitterProfile_User_EmailAndService_Id(sitter.getEmail(), bookingDetail.getId());
                    Long commission = sitterService.getService().getCommission();
                    deposit = deposit.add(sitterService.getPrice().multiply(BigDecimal.valueOf(bookingDetail.getDuration()).divide(BigDecimal.valueOf(sitterService.getService().getDuration()))).multiply((BigDecimal.valueOf(commission).divide(BigDecimal.valueOf(100)))));
                    total = total.add(sitterService.getPrice().multiply(BigDecimal.valueOf(bookingDetail.getDuration()).divide(BigDecimal.valueOf(sitterService.getService().getDuration()))));

                    BigDecimal price = (sitterService.getPrice().multiply(BigDecimal.valueOf(bookingDetail.getDuration()).divide(BigDecimal.valueOf(sitterService.getService().getDuration()))));

                    bookingDetail.setPrice(price.multiply(BigDecimal.valueOf(booking.getWorkingTimes().size())));
                    bookingDetailRepository.save(bookingDetail);
                }
                booking.setSitter(sitter);
                booking.setTotalPrice(total.multiply(BigDecimal.valueOf(booking.getWorkingTimes().size())));
                booking.setDeposit(deposit.multiply(BigDecimal.valueOf(booking.getWorkingTimes().size())));
                notificationService.sendNotification(booking.getSitter().getId(), "Bạn nhận được một booking mới","Mau mau kiểm tra lịch trình của bạn");
                bookingRepository.save(booking);
            }else {
                booking.setSitter(null);
                booking.setTotalPrice(null);
                booking.setStatus(StatusCode.SITTER_NOT_FOUND.toString());
                booking.setDeposit(null);
                for (BookingDetail bookingDetail: bookingDetails) {
                    bookingDetail.setPrice(null);
                    bookingDetailRepository.save(bookingDetail);
                }
                notificationService.sendNotification(booking.getUser().getId(), "Không có sitter nào phù hợp với đơn của bạn \n Hoặc nhân viên chúng tôi hiện chưa thể nhận đơn hàng của bạn","Vui lòng chọn dịch vụ khác hoặc thử lại sao");
                bookingRepository.save(booking);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return sitter;

    }

}
