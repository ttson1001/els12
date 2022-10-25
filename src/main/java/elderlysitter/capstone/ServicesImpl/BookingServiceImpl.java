package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.BookingService;
import elderlysitter.capstone.dto.BookingRequestDTO;
import elderlysitter.capstone.entities.Booking;
import elderlysitter.capstone.entities.BookingDetail;
import elderlysitter.capstone.entities.Service;
import elderlysitter.capstone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    SitterServiceRepository sitterServiceRepository;

    @Override
    public Booking addBookingService(BookingRequestDTO bookingRequestDTO) {
        UUID uuid = UUID.randomUUID();
        bookingRequestDTO.getTotalPrice();


        Booking newBooking = Booking.builder()
                .name(uuid.toString())
                .address(bookingRequestDTO.getAddress())
                .place(bookingRequestDTO.getPlace())
                .description(bookingRequestDTO.getDescription())
                .startDateTime(bookingRequestDTO.getStartDateTime())
                .endDateTime(bookingRequestDTO.getEndDateTime())
                .status(statusRepository.findByStatusName("WAITING_FOR_SITTER"))
                .elderId(Long.parseLong(bookingRequestDTO.getElderId()))
                .user(userRepository.findUserByEmail(bookingRequestDTO.getEmail()))
                .build();
        bookingRepository.save(newBooking);
        Booking booking = bookingRepository.findBookingByName(newBooking.getName());
        for (int i = 0; i< bookingRequestDTO.getBookingServiceRequestDTOS().size(); i++){
            Service service = serviceRepository.getById(bookingRequestDTO.getBookingServiceRequestDTOS().get(i).getId());
            BookingDetail bookingDetail = BookingDetail.builder()
                    .booking(booking)
                    .service(service)
                    .duration(bookingRequestDTO.getBookingServiceRequestDTOS().get(i).getDuration())
//                    .price()
                    .build();
            bookingDetailRepository.save(bookingDetail);
        }
        return booking;

    }

//    @Override
//    public Booking bookingSitter(BookingSitterRequestDTO bookingSitterRequestDTO) {
//        UUID uuid = UUID.randomUUID();
//        Booking newBooking = Booking.builder()
//                .name(uuid.toString())
//                .address(bookingSitterRequestDTO.getAddress())
//                .place(bookingSitterRequestDTO.getPlace())
//                .description(bookingSitterRequestDTO.getDescription())
//                .startDateTime(bookingSitterRequestDTO.getStartDateTime())
//                .endDateTime(bookingSitterRequestDTO.getEndDateTime())
//                .totalPrice(BigDecimal.valueOf(Double.valueOf(bookingSitterRequestDTO.getTotalPrice())))
//                .elderId(Long.parseLong(bookingSitterRequestDTO.getElderId()))
//                .status(statusRepository.findByStatusName("WAITING_FOR_SITTER"))
//                .sitter(userRepository.findById(Long.parseLong(bookingSitterRequestDTO.getSitterId())).get())
//                .user(userRepository.findUserByEmail(bookingSitterRequestDTO.getEmail()))
//                .build();
//        bookingRepository.save(newBooking);
//        Booking booking = bookingRepository.findBookingByName(newBooking.getName());
//        for (int i = 0; i< bookingSitterRequestDTO.getServiceIds().size(); i++){
//            Service service = serviceRepository.getById(Long.parseLong(bookingSitterRequestDTO.getServiceIds().get(i)));
//            BookingDetail bookingDetail = BookingDetail.builder()
//                    .booking(booking)
//                    .service(service)
//                    .duration(service.getDuration())
//                    .build();
//            bookingDetailRepository.save(bookingDetail);
//        }
//        return booking;
//    }

    @Override
    public List<Booking> getListBookingByStatus(String statusEmail) {
        List<Booking> bookings = bookingRepository.findAllByStatus_StatusName(statusEmail);
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

    @Override
    public List<Booking> getAllBookingByCustomerEmailAndStatusName(String email, String statusName) {
        List<Booking> bookingList = bookingRepository.findAllByUser_EmailAndStatus_StatusName(email,statusName);
        return bookingList;
    }

    @Override
    public List<Booking> getAllBookingBySitterEmailAndStatusName(String email, String statusName) {
        List<Booking> bookingList = bookingRepository.findAllBySitter_EmailAndStatus_StatusName(email,statusName);
        return bookingList;
    }
}
