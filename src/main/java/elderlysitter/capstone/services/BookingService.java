package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.BookingDTO;
import elderlysitter.capstone.dto.request.AddBookingRequestDTO;
import elderlysitter.capstone.dto.response.BookingResponseDTO;
import elderlysitter.capstone.dto.response.BookingsResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    BookingDTO addBooking(AddBookingRequestDTO addBookingRequestDTO);

    List<BookingsResponseDTO> getAllBooking();

    BookingResponseDTO getBookingById(Long id);

    List<BookingsResponseDTO> getAllBookingByStatus(String status);

    BookingDTO acceptBookingForSitter(Long id);

    Long countBooking(LocalDateTime startDate, LocalDateTime endDate);
}
