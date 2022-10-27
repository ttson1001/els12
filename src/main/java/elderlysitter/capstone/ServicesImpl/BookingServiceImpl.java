package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.BookingService;
import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.BookingRequestDTO;
import elderlysitter.capstone.dto.BookingServiceRequestDTO;
import elderlysitter.capstone.entities.*;
import elderlysitter.capstone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Autowired
    UserService userService;


    @Override
    public Booking addBookingService(BookingRequestDTO bookingRequestDTO) {
        UUID uuid = UUID.randomUUID();

        List<BookingServiceRequestDTO> bookingServiceRequestDTOS =bookingRequestDTO.getBookingServiceRequestDTOS();

        User user =userService.getAllSitterByBookingServiceRequestDTO(bookingServiceRequestDTOS," ");
        if(user == null )
        {
            return null;
        }
        BigDecimal total = BigDecimal.valueOf(0D);

        for (BookingServiceRequestDTO bookingServiceRequestDTO: bookingServiceRequestDTOS){
            SitterService sitterService = sitterServiceRepository.findBySitterProfile_User_EmailAndService_Id(user.getEmail(),bookingServiceRequestDTO.getId());
            total = total.add(sitterService.getPrice().multiply(BigDecimal.valueOf(bookingServiceRequestDTO.getDuration())));

        }


        Booking newBooking = Booking.builder()
                .name(uuid.toString())
                .address(bookingRequestDTO.getAddress())
                .place(bookingRequestDTO.getPlace())
                .description(bookingRequestDTO.getDescription())
                .startDateTime(bookingRequestDTO.getStartDateTime())
                .endDateTime(bookingRequestDTO.getEndDateTime())
                .status(statusRepository.findByStatusName("WAITING_FOR_SITTER"))
                .elderId(Long.parseLong(bookingRequestDTO.getElderId()))
                .sitter(userRepository.findUserByEmail(user.getEmail()))
                .totalPrice(total)
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
                    .price((user.getSitterProfile().getSitterService().get(i).getPrice().multiply(BigDecimal.valueOf(bookingRequestDTO.getBookingServiceRequestDTOS().get(i).getDuration()))))
                    .build();
            bookingDetailRepository.save(bookingDetail);
        }

        return booking;

    }

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

    @Override
    public Booking cancelBookingSitter(Long bookingId, String email) {
        Booking booking = bookingRepository.findById(bookingId).get();
        booking.setSitter(userRepository.findUserByEmail(email));

        List<BookingDetail> bookingDetails = booking.getBookingDetails();
        List<BookingServiceRequestDTO> bookingServiceRequestDTOS  = new ArrayList<>();
        for (BookingDetail bookingDetail: bookingDetails){
            BookingServiceRequestDTO bookingServiceRequestDTO = BookingServiceRequestDTO.builder()
                    .id(bookingDetail.getService().getId())
                    .duration(bookingDetail.getDuration())
                    .build();
            bookingServiceRequestDTOS.add(bookingServiceRequestDTO);
        }

        User user =userService.getAllSitterByBookingServiceRequestDTO(bookingServiceRequestDTOS,email);
        if(user == null )
        {
            return null;
        }
        List<BookingDetail> bookingDetails1 = bookingDetailRepository.findAllByBooking_Id(bookingId);
        BigDecimal total = BigDecimal.valueOf(0D);
        for (BookingDetail bookingDetail: bookingDetails1){
            BigDecimal price = sitterServiceRepository.findBySitterProfile_User_EmailAndService_Id(user.getEmail(),bookingDetail.getService().getId()).getPrice();
            bookingDetail.setPrice(price.multiply(BigDecimal.valueOf(bookingDetail.getDuration())));
            total = total.add(price.multiply(BigDecimal.valueOf(bookingDetail.getDuration())));
            bookingDetailRepository.save(bookingDetail);
        }


        booking.setTotalPrice(total);


        return  bookingRepository.save(booking);
    }

    @Override
    public Booking acceptBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        booking.setStatus(statusRepository.findByStatusName("WAITING_TO_START_DATE"));
        bookingRepository.save(booking);
        return booking;
    }
}

