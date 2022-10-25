package elderlysitter.capstone.ServicesImpl;

import com.fasterxml.jackson.databind.node.DecimalNode;
import elderlysitter.capstone.Services.BookingService;
import elderlysitter.capstone.dto.BookingDTO;
import elderlysitter.capstone.dto.BookingSitterDTO;
import elderlysitter.capstone.entities.Booking;
import elderlysitter.capstone.entities.BookingDetail;
import elderlysitter.capstone.entities.Service;
import elderlysitter.capstone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingDetailRepository bookingDetailRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ElderRepository elderRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StatusRepository statusRepository;

    @Override
    public Booking addBookingService(BookingDTO bookingDTO) {
        UUID uuid = UUID.randomUUID();
        Booking newBooking = Booking.builder()
                .name(uuid.toString())
                .address(bookingDTO.getAddress())
                .place(bookingDTO.getPlace())
                .description(bookingDTO.getDescription())
                .startDateTime(bookingDTO.getStartDateTime())
                .endDateTime(bookingDTO.getEndDateTime())
                .status(statusRepository.findByStatusName("CREATE"))
                .elderId(Long.parseLong(bookingDTO.getElderId()))
                .user(userRepository.findUserByEmail(bookingDTO.getEmail()))
                .build();
        bookingRepository.save(newBooking);
        Booking booking = bookingRepository.findBookingByName(newBooking.getName());
        for (int i = 0 ; i<bookingDTO.getServiceIds().size(); i++){
            Service service = serviceRepository.getById(Long.parseLong(bookingDTO.getServiceIds().get(i)));
            BookingDetail bookingDetail = BookingDetail.builder()
                    .booking(booking)
                    .service(service)
                    .duration(service.getDuration())
                    .build();
            bookingDetailRepository.save(bookingDetail);
        }
        return booking;

    }

    @Override
    public Booking bookingSitter(BookingSitterDTO bookingSitterDTO) {
        UUID uuid = UUID.randomUUID();
        Booking newBooking = Booking.builder()
                .name(uuid.toString())
                .address(bookingSitterDTO.getAddress())
                .place(bookingSitterDTO.getPlace())
                .description(bookingSitterDTO.getDescription())
                .startDateTime(bookingSitterDTO.getStartDateTime())
                .endDateTime(bookingSitterDTO.getEndDateTime())
                .totalPrice(BigDecimal.valueOf(Double.valueOf(bookingSitterDTO.getTotalPrice())))
                .elderId(Long.parseLong(bookingSitterDTO.getElderId()))
                .status(statusRepository.findByStatusName("WAITING_FOR_SITTER"))
                .sitter(userRepository.findById(Long.parseLong(bookingSitterDTO.getSitterId())).get())
                .user(userRepository.findUserByEmail(bookingSitterDTO.getEmail()))
                .build();
        bookingRepository.save(newBooking);
        Booking booking = bookingRepository.findBookingByName(newBooking.getName());
        for (int i = 0 ; i<bookingSitterDTO.getServiceIds().size(); i++){
            Service service = serviceRepository.getById(Long.parseLong(bookingSitterDTO.getServiceIds().get(i)));
            BookingDetail bookingDetail = BookingDetail.builder()
                    .booking(booking)
                    .service(service)
                    .duration(service.getDuration())
                    .build();
            bookingDetailRepository.save(bookingDetail);
        }
        return booking;
    }

    @Override
    public List<Booking> getListBookingBYStatus(Long statusId) {
        List<Booking> bookings = bookingRepository.findAllByStatus_Id(statusId);
        return bookings;
    }

    @Override
    public List<Booking> getAllBooking() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings;
    }

    @Override
    public List<Booking> getAllBookingByCustomerEmail(String email) {
        List<Booking> bookings = bookingRepository.findAllByUser_Email(email);
        return bookings;
    }

    @Override
    public List<Booking> getAllBookingBySitterEmail(String email) {
        List<Booking> bookings = bookingRepository.findAllBySitter_Email(email);
        return bookings;
    }

    @Override
    public List<BookingDetail> getAllBookingDetailByBookingId(Long bookingId) {
        List<BookingDetail> bookingDetails = bookingDetailRepository.findAllByBooking_Id(bookingId);
        return bookingDetails;
    }

    @Override
    public Booking acceptBookingBySitter(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        booking.setStatus(statusRepository.findByStatusName("S"));
        return booking;
    }
}
