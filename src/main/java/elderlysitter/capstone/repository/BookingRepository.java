package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findBookingByName(String name);

    List<Booking> findAllByStatus_StatusName(String name);

    List<Booking> findAllByUser_Email(String customerEmail);

    List<Booking> findAllBySitter_Email(String sitterEmail);

    List<Booking> findAllByUser_EmailAndStatus_StatusName(String email, String statusName);

    List<Booking> findAllBySitter_EmailAndStatus_StatusName(String email, String statusName);


}
