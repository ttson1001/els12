package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.BookingDTO;
import elderlysitter.capstone.dto.BookingSitterDTO;
import elderlysitter.capstone.entities.Booking;

public interface BookingService {
    Booking addBookingService(BookingDTO bookingDTO);

    Booking bookingSitter(BookingSitterDTO bookingSitterDTO);
}
