package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findBookingByName(String name);
}
