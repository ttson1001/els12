package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.BookingDTO;
import elderlysitter.capstone.entities.Booking;

public interface BookingService {
    Booking addBookingService(BookingDTO bookingDTO);
}
