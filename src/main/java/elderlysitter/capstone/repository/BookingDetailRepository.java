package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
}
