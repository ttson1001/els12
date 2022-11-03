package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.BookingDetailResponseDTO;
import elderlysitter.capstone.dto.BookingRequestDTO;
import elderlysitter.capstone.entities.Booking;
import elderlysitter.capstone.entities.BookingDetail;

import java.util.List;

public interface BookingService {
    Booking addBooking(BookingRequestDTO bookingRequestDTO);

//    Booking bookingSitter(BookingSitterRequestDTO bookingSitterRequestDTO);

    List<Booking> getListBookingByStatus(String name);

    List<Booking> getAllBooking();

    List<Booking> getAllBookingByCustomerEmail(String email);
    List<Booking> getAllBookingBySitterEmail(String email);

    List<BookingDetailResponseDTO> getAllBookingDetailByBookingId(Long bookingId);

    Booking acceptBookingBySitter(Long bookingId);

    List<Booking> getAllBookingByCustomerEmailAndStatusName(String email, String statusName);

    List<Booking> getAllBookingBySitterEmailAndStatusName(String email, String statusName);

    Booking cancelBookingSitter(Long bookingId , String email);

    Booking acceptBooking(Long bookingId);

    Booking fakePayment(Long bookingId);
}
