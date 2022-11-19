package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.BookingImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingImgRepository extends JpaRepository<BookingImg,Long> {
    BookingImg findByBooking_Id(Long bookingID);
}
