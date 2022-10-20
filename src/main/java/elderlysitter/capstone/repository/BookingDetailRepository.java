package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
    List<BookingDetail> findAllByBooking_Id(Long bookingId);
}
