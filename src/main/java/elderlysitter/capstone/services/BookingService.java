package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.BookingDTO;
import elderlysitter.capstone.dto.request.AddBookingRequestDTO;
import elderlysitter.capstone.dto.response.BookingResponseDTO;
import elderlysitter.capstone.dto.response.BookingsResponseDTO;
import elderlysitter.capstone.entities.Booking;

import java.math.BigDecimal;
import java.util.List;

public interface BookingService {
    BookingDTO addBooking(AddBookingRequestDTO addBookingRequestDTO);

    List<BookingsResponseDTO> getAllBooking();

    BookingResponseDTO getBookingById(Long id);

    List<BookingsResponseDTO> getAllBookingByStatus(String status);

    BookingDTO acceptBookingForSitter(Long id);
    List<BookingsResponseDTO> findAllByCustomerEmail(String customerEmail);
    List<BookingsResponseDTO> findAllBySitterEmail(String sitterEmail);
    List<BookingsResponseDTO> findAllByCustomerEmailAndStatus(String customerEmail, String status);
    List<BookingsResponseDTO> findAllBySitter_EmailAndStatus(String sitterEmail, String status);

    Long countBooking(String startDateTime, String endDateTime);

    BigDecimal sumDeposit(String startDateTime, String endDateTime);
}
