package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.request.AddBookingRequestDTO;
import elderlysitter.capstone.dto.request.AddBookingServiceRequestDTO;
import elderlysitter.capstone.dto.request.AddPaymentRequestDTO;
import elderlysitter.capstone.dto.request.AddWorkingTimesRequestDTO;
import elderlysitter.capstone.dto.response.PaymentResponseDTO;
import elderlysitter.capstone.entities.*;
import elderlysitter.capstone.enumCode.StatusCode;
import elderlysitter.capstone.repository.*;
import elderlysitter.capstone.services.BookingService;
import elderlysitter.capstone.services.NotificationService;
import elderlysitter.capstone.services.PaymentService;
import elderlysitter.capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SitterServiceRepository sitterServiceRepository;

    @Override
    public PaymentResponseDTO paid(AddPaymentRequestDTO addPaymentRequestDTO) {
        PaymentResponseDTO paymentResponseDTO = null;
        try {
            Booking booking = bookingRepository.findById(addPaymentRequestDTO.getBookingId()).get();
            booking.setStatus(StatusCode.WAITING_FOR_DATE.toString());
            booking = bookingRepository.save(booking);
            Payment payment = Payment.builder()
                    .paymentType(addPaymentRequestDTO.getPaymentType())
                    .user(userRepository.findUserByEmail(addPaymentRequestDTO.getEmail()))
                    .dateTime(LocalDateTime.now())
                    .booking(booking)
                    .build();
            payment = paymentRepository.save(payment);
            notificationService.sendNotification(booking.getSitter().getId(),  "Kh??ch h??ng ???? thanh to??n th??nh c??ng\n","B???n h??y ki???m tra l???ch tr??nh ????? b???t ?????u l??m vi???c");
            paymentResponseDTO = PaymentResponseDTO.builder()
                    .paymentType(payment.getPaymentType())
                    .amount(payment.getAmount())
                    .cusName(payment.getUser().getFullName())
                    .dateTime(payment.getDateTime())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentResponseDTO;
    }

    @Override
    public PaymentResponseDTO paidBeforeChangeSitter(AddPaymentRequestDTO addPaymentRequestDTO) {
        PaymentResponseDTO paymentResponseDTO = null;
        try {
            Booking booking = bookingRepository.findById(addPaymentRequestDTO.getBookingId()).get();
            booking.setStatus(StatusCode.DONE.toString());
            booking = bookingRepository.save(booking);
            Payment payment = Payment.builder()
                    .paymentType(addPaymentRequestDTO.getPaymentType())
                    .user(userRepository.findUserByEmail(addPaymentRequestDTO.getEmail()))
                    .dateTime(LocalDateTime.now())
                    .booking(booking)
                    .build();
            payment = paymentRepository.save(payment);
            notificationService.sendNotification(booking.getSitter().getId(),  "B???n ???? thanh to??n th??nh c??ng\n","B???n h??y ki???m tra l???ch tr??nh ????? b???t ?????u l??m vi???c");
            addNewBooking(booking);
            paymentResponseDTO = PaymentResponseDTO.builder()
                    .paymentType(payment.getPaymentType())
                    .amount(payment.getAmount())
                    .cusName(payment.getUser().getFullName())
                    .dateTime(payment.getDateTime())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentResponseDTO;
    }

    private void addNewBooking(Booking booking) {
        UUID uuid = UUID.randomUUID();
        BigDecimal total = BigDecimal.valueOf(0);
        BigDecimal deposit = BigDecimal.valueOf(0);
        try {
            List<AddBookingServiceRequestDTO> addBookingServiceRequestDTOS = new ArrayList<>();
            List<AddWorkingTimesRequestDTO> addWorkingTimesRequestDTOS = new ArrayList<>();
            List<BookingDetail> bookingDetails = booking.getBookingDetails();
            List<WorkingTime> newWorkingTimes = new ArrayList<>();
            for (BookingDetail bookingDetail : bookingDetails) {
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
            User sitter = userService.randomSitter(addBookingRequestDTO, booking.getSitter().getEmail());
            if (sitter != null) {
                Booking newBooking = Booking.builder()
                        .name(uuid.toString())
                        .address(booking.getAddress())
                        .description(booking.getDescription())
                        .status(StatusCode.WAITING_FOR_SITTER.toString())
                        .elder(booking.getElder())
                        .sitter(sitter)
//                    .deposit(deposit)
//                    .totalPrice(total.multiply(BigDecimal.valueOf(addWorkingTimesRequestDTOS.size())))
                        .user(booking.getUser())
                        .build();
                Booking _booking = bookingRepository.save(newBooking);

                for (WorkingTime workingTime : newWorkingTimes
                ) {
                    if (workingTime.getStatus().equalsIgnoreCase(StatusCode.ACTIVATE.toString())) {
                        WorkingTime newWorkingTime = WorkingTime.builder()
                                .startDateTime(workingTime.getStartDateTime())
                                .endDateTime(workingTime.getEndDateTime())
                                .booking(_booking)
                                .build();
                        workingTimes.add(newWorkingTime);
                    }
                }
                List<BookingDetail> _BookingDetailList = new ArrayList<>();

                for (BookingDetail bookingDetail : bookingDetails) {
                    SitterService sitterService = sitterServiceRepository.findBySitterProfile_User_EmailAndService_Id(sitter.getEmail(), bookingDetail.getId());
                    Long commission = sitterService.getService().getCommission();
                    deposit = deposit.add(sitterService.getPrice().multiply(BigDecimal.valueOf(bookingDetail.getDuration()).divide(BigDecimal.valueOf(sitterService.getService().getDuration()))).multiply((BigDecimal.valueOf(commission).divide(BigDecimal.valueOf(100)))));
                    total = total.add(sitterService.getPrice().multiply(BigDecimal.valueOf(bookingDetail.getDuration()).divide(BigDecimal.valueOf(sitterService.getService().getDuration()))));

                    BigDecimal price = (sitterService.getPrice().multiply(BigDecimal.valueOf(bookingDetail.getDuration()).divide(BigDecimal.valueOf(sitterService.getService().getDuration()))));

                    bookingDetail.setPrice(price.multiply(BigDecimal.valueOf(newWorkingTimes.size())));
                    _BookingDetailList.add(bookingDetail);
                }
                _booking.setTotalPrice(total.multiply(BigDecimal.valueOf(newWorkingTimes.size())));
                _booking.setDeposit(deposit.multiply(BigDecimal.valueOf(newWorkingTimes.size())));
                _booking.setBookingDetails(_BookingDetailList);
                _booking.setWorkingTimes(newWorkingTimes);

                bookingRepository.save(_booking);
            } else {
                Booking newBooking = Booking.builder()
                        .name(uuid.toString())
                        .address(booking.getAddress())
                        .description(booking.getDescription())
                        .status(StatusCode.SITTER_NOT_FOUND.toString())
                        .elder(booking.getElder())
                        .sitter(null)
                        .deposit(null)
                        .totalPrice(null)
                        .workingTimes(newWorkingTimes)
                        .user(booking.getUser())
                        .build();
                Booking _booking = bookingRepository.save(newBooking);
                List<BookingDetail> _BookingDetailList = new ArrayList<>();
                for (BookingDetail bookingDetail : bookingDetails) {
                    BookingDetail _bookingDetail = BookingDetail.builder()
                            .id(bookingDetail.getId())
                            .service(bookingDetail.getService())
                            .booking(_booking)
                            .commission(bookingDetail.getCommission())
                            .price(null)
                            .duration(bookingDetail.getDuration())
                            .serviceName(bookingDetail.getServiceName())
                            .build();
                    _BookingDetailList.add(_bookingDetail);
                }
                _booking.setBookingDetails(_BookingDetailList);
                bookingRepository.save(booking);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
