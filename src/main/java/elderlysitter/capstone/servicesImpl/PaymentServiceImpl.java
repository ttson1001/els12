package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.request.AddPaymentRequestDTO;
import elderlysitter.capstone.dto.response.PaymentResponseDTO;
import elderlysitter.capstone.entities.Booking;
import elderlysitter.capstone.entities.Payment;
import elderlysitter.capstone.enumCode.StatusCode;
import elderlysitter.capstone.repository.BookingRepository;
import elderlysitter.capstone.repository.PaymentRepository;
import elderlysitter.capstone.repository.UserRepository;
import elderlysitter.capstone.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;
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
            paymentResponseDTO = PaymentResponseDTO.builder()
                    .paymentType(payment.getPaymentType())
                    .amount(payment.getAmount())
                    .cusName(payment.getUser().getFullName())
                    .dateTime(payment.getDateTime())
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return paymentResponseDTO;
    }
}
