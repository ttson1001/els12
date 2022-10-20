package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findBookingByName(String name);

    List<Booking> findAllByStatus_Id(Long statusId);

    List<Booking> findAllByUser_Email(String customerId);
}
