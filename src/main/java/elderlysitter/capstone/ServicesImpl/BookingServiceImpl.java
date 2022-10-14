package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.BookingService;
import elderlysitter.capstone.dto.BookingDTO;
import elderlysitter.capstone.dto.BookingSitterDTO;
import elderlysitter.capstone.entities.Booking;
import elderlysitter.capstone.entities.BookingDetail;
import elderlysitter.capstone.entities.Service;
import elderlysitter.capstone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Override
    public Booking addBookingService(BookingDTO bookingDTO) {
        Booking newBooking = Booking.builder()
                .name(bookingDTO.getName())
                .address(bookingDTO.getAddress())
                .place(bookingDTO.getPlace())
                .description(bookingDTO.getDescription())
                .startDate(bookingDTO.getStartDateTime())
                .endDate(bookingDTO.getEndDateTime())
                .totalPrice(bookingDTO.getTotalPrice())
                .elderId(bookingDTO.getElderId())
                .user(userRepository.findUserByEmail(bookingDTO.getEmail()))
                .build();
        bookingRepository.save(newBooking);
        Booking booking = bookingRepository.findBookingByName(bookingDTO.getName());
        for (int i = 0 ; i<bookingDTO.getServiceIds().size(); i++){
            Service service = serviceRepository.getById(bookingDTO.getServiceIds().get(i));
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
        Booking newBooking = Booking.builder()
                .name(bookingSitterDTO.getName())
                .address(bookingSitterDTO.getAddress())
                .place(bookingSitterDTO.getPlace())
                .description(bookingSitterDTO.getDescription())
                .startDate(bookingSitterDTO.getStartDateTime())
                .endDate(bookingSitterDTO.getEndDateTime())
                .totalPrice(bookingSitterDTO.getTotalPrice())
                .elderId(bookingSitterDTO.getElderId())
                .sitter_id(bookingSitterDTO.getSitterId())
                .user(userRepository.findUserByEmail(bookingSitterDTO.getEmail()))
                .build();
        bookingRepository.save(newBooking);
        Booking booking = bookingRepository.findBookingByName(bookingSitterDTO.getName());
        for (int i = 0 ; i<bookingSitterDTO.getServiceIds().size(); i++){
            Service service = serviceRepository.getById(bookingSitterDTO.getServiceIds().get(i));
            BookingDetail bookingDetail = BookingDetail.builder()
                    .booking(booking)
                    .service(service)
                    .duration(service.getDuration())
                    .build();
            bookingDetailRepository.save(bookingDetail);
        }
        return booking;
    }
}
