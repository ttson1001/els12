package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.BookingDTO;
import elderlysitter.capstone.dto.BookingSitterDTO;
import elderlysitter.capstone.entities.Booking;
import elderlysitter.capstone.entities.BookingDetail;

import java.util.List;

public interface BookingService {
    Booking addBookingService(BookingDTO bookingDTO);

    Booking bookingSitter(BookingSitterDTO bookingSitterDTO);

    List<Booking> getListBookingBYStatus(Long statusId);

    List<Booking> getAllBooking();

    List<Booking> getAllBookingByCustomerEmail(String email);
    List<Booking> getAllBookingBySitterEmail(String email);

    List<BookingDetail> getAllBookingDetailByBookingId(Long bookingId);

    Booking acceptBookingBySitter(Long bookingId);
}
