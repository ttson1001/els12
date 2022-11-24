package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.RatingDTO;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
                    .onTime(addRatingRequestDTO.getOnTime())
                    .diligent(addRatingRequestDTO.getDiligent())
                    .enthusiasm(addRatingRequestDTO.getEnthusiasm())
                    .comment(addRatingRequestDTO.getComment())
                    .build();
            rating = ratingRepository.save(rating);
            ratingResponseDTO = RatingResponseDTO.builder()
                    .sitterDTO(sitterDTO)
                    .rate(rating.getStar())
                    .onTime(rating.getOnTime())
                    .diligent(rating.getDiligent())
                    .enthusiasm(rating.getEnthusiasm())
                    .comment(rating.getComment())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ratingResponseDTO;
    }

    @Override
    public List<RatingResponseDTO> findAllBySitter_Email(String email) {
        List<RatingResponseDTO> ratingResponseDTOS = new ArrayList<>();
        try {
            List<Rating> ratings = ratingRepository.findAllBySitter_Email(email);
            for (Rating rating : ratings
            ) {
                SitterDTO sitterDTO = SitterDTO.builder()
                        .id(rating.getSitter().getId())
                        .avatarImgUrl(rating.getSitter().getAvatarImgUrl())
                        .dob(rating.getSitter().getDob())
                        .email(rating.getSitter().getEmail())
                        .phone(rating.getSitter().getPhone())
                        .address(rating.getSitter().getAddress())
                        .gender(rating.getSitter().getGender())
                        .fullName(rating.getSitter().getFullName())
                        .build();
                RatingResponseDTO ratingResponseDTO = RatingResponseDTO.builder()
                        .rate(rating.getStar())
                        .comment(rating.getComment())
                        .sitterDTO(sitterDTO)
                        .onTime(rating.getOnTime())
                        .enthusiasm(rating.getEnthusiasm())
                        .diligent(rating.getDiligent())
                        .build();
                ratingResponseDTOS.add(ratingResponseDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ratingResponseDTOS;

    }

    @Override
    public RatingDTO countRatingBySitter(String email) {
        RatingDTO ratingDTO = null;
        Long sitterID = 0L;
        Long numberOfOnTime = 0L;
        Long numberOfDiligent = 0L;
        Long numberOfEnthusiasm = 0L;
        try {
            List<Rating> ratings = ratingRepository.findAllBySitter_Email(email);
            for (Rating rating : ratings
            ) {
                if(rating.getOnTime() == true) numberOfOnTime = numberOfOnTime+1L;
                if(rating.getDiligent() == true) numberOfDiligent = numberOfDiligent+1L;
                if(rating.getEnthusiasm() == true) numberOfEnthusiasm = numberOfEnthusiasm+1L;
                sitterID = rating.getSitter().getId();
            }
            ratingDTO = RatingDTO.builder()
                    .sitterId(sitterID)
                    .numberOfDiligent(numberOfDiligent)
                    .numberOfOnTime(numberOfOnTime)
                    .numberOfEnthusiasm(numberOfEnthusiasm)
                    .email(email)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ratingDTO;
    }



}
