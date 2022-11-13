package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.request.AddBookingImgRequestDTO;
import elderlysitter.capstone.dto.response.BookingImgResponseDTO;

public interface BookingImgService {
    BookingImgResponseDTO checkIn(AddBookingImgRequestDTO addBookingImgRequestDTO);

    BookingImgResponseDTO checkOut(AddBookingImgRequestDTO addBookingImgRequestDTO);
}
