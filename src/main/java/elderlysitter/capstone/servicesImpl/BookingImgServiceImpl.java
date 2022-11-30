package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.request.AddBookingImgRequestDTO;
import elderlysitter.capstone.dto.response.BookingImgResponseDTO;
import elderlysitter.capstone.entities.Booking;
import elderlysitter.capstone.entities.BookingImg;
import elderlysitter.capstone.entities.WorkingTime;
import elderlysitter.capstone.enumCode.StatusCode;
import elderlysitter.capstone.repository.BookingImgRepository;
import elderlysitter.capstone.repository.BookingRepository;
import elderlysitter.capstone.repository.WorkingTimeRepository;
import elderlysitter.capstone.services.BookingImgService;
import elderlysitter.capstone.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingImgServiceImpl implements BookingImgService {
    @Autowired
    private BookingImgRepository bookingImgRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @Override
    public BookingImgResponseDTO checkIn(AddBookingImgRequestDTO addBookingImgRequestDTO) {
        BookingImgResponseDTO bookingImgResponseDTO = null;
        try {
            Booking booking = bookingRepository.findById(addBookingImgRequestDTO.getBookingId()).get();
            BookingImg bookingImg = BookingImg.builder()
                    .booking(booking)
                    .checkInUrl(addBookingImgRequestDTO.getUrl())
                    .localDateTime(LocalDateTime.now())
                    .build();
            bookingImg = bookingImgRepository.save(bookingImg);
            booking.setStatus(StatusCode.STARTING.toString());
            bookingRepository.save(booking);
            bookingImgResponseDTO = BookingImgResponseDTO.builder()
                    .localDateTime(bookingImg.getLocalDateTime())
                    .checkInUrl(bookingImg.getCheckInUrl())
                    .build();
            notificationService.sendNotification(booking.getUser().getId(),  "Sitter đã bắt đầu làm việc","Mau mau kiểm tra lịch trình của bạn để theo dõi quá trình làm việc của sitter");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingImgResponseDTO;
    }

    @Override
    public BookingImgResponseDTO checkOut(AddBookingImgRequestDTO addBookingImgRequestDTO) {
        BookingImgResponseDTO bookingImgResponseDTO = null;
        LocalDateTime now = LocalDateTime.now();

        try {
            Booking booking = bookingRepository.findById(addBookingImgRequestDTO.getBookingId()).get();
            LocalDate endDate = booking.getWorkingTimes().get(booking.getWorkingTimes().size() - 1).getEndDateTime().toLocalDate();
            BookingImg bookingImg = bookingImgRepository.findByBooking_Id(addBookingImgRequestDTO.getBookingId());
            bookingImg.setCheckOutUrl(addBookingImgRequestDTO.getUrl());
            bookingImg = bookingImgRepository.save(bookingImg);
            if (now.plusHours(7).toLocalDate().isEqual(endDate)) {
                booking.setStatus(StatusCode.WAITING_FOR_CUSTOMER_CHECK.toString());
                notificationService.sendNotification(booking.getUser().getId(),  "Sitter đã hoàn thành xong đơn hàng\n Vui lòng kiểm tra lại và trả tiền cho sitter","Mau mau kiểm tra lịch trình của bạn để theo dõi quá trình làm việc của sitter");
            } else {
                booking.setStatus(StatusCode.WAITING_FOR_NEXT_DATE.toString());
                notificationService.sendNotification(booking.getUser().getId(),  "Sitter đã hoàn thành xong ngày làm việc hôm nay\n Vui lòng kiểm tra lại","Mau mau kiểm tra lịch trình của bạn để theo dõi quá trình làm việc của sitter");

            }
            List<WorkingTime> workingTimes = booking.getWorkingTimes();
            for (WorkingTime workingTime : workingTimes
            ) {
                if(workingTime.getEndDateTime().toLocalDate().isEqual(now.plusHours(7).toLocalDate())){
                    workingTime.setStatus("DONE");
                    workingTimeRepository.save(workingTime);
                }
            }
            bookingRepository.save(booking);
            bookingImgResponseDTO = BookingImgResponseDTO.builder()
                    .localDateTime(bookingImg.getLocalDateTime())
                    .checkOutUrl(bookingImg.getCheckOutUrl())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingImgResponseDTO;
    }
}
