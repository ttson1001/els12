package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.SitterDTO;
import elderlysitter.capstone.dto.request.AddRatingRequestDTO;
import elderlysitter.capstone.dto.response.RatingResponseDTO;
import elderlysitter.capstone.entities.Booking;
import elderlysitter.capstone.entities.Rating;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.BookingRepository;
import elderlysitter.capstone.repository.RatingRepository;
import elderlysitter.capstone.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public RatingResponseDTO rateToSitter(AddRatingRequestDTO addRatingRequestDTO) {
        RatingResponseDTO ratingResponseDTO = null;
        try {
            Booking booking = bookingRepository.findById(addRatingRequestDTO.getBookingId()).get();
            User sitter = booking.getSitter();
            SitterDTO sitterDTO = SitterDTO.builder()
                    .id(sitter.getId())
                    .avatarImgUrl(sitter.getAvatarImgUrl())
                    .dob(sitter.getDob())
                    .email(sitter.getEmail())
                    .phone(sitter.getPhone())
                    .address(sitter.getAddress())
                    .gender(sitter.getGender())
                    .fullName(sitter.getFullName())
                    .build();
            Rating rating = Rating.builder()
                    .booking(booking)
                    .sitter(booking.getSitter())
                    .star(addRatingRequestDTO.getStar())
                    .comment(addRatingRequestDTO.getComment())
                    .build();
            rating = ratingRepository.save(rating);
            ratingResponseDTO = RatingResponseDTO.builder()
                    .sitterDTO(sitterDTO)
                    .rate(rating.getStar())
                    .comment(rating.getComment())
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ratingResponseDTO;
    }



}
