package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findBookingByName(String name);

    List<Booking> findAllByStatus(String name);

    List<Booking> findAllByUser_Email(String customerEmail);

    List<Booking> findAllBySitter_Email(String sitterEmail);

    List<Booking> findAllByUser_EmailAndStatus(String email, String statusName);

    List<Booking> findAllBySitter_EmailAndStatus(String email, String statusName);

    @Query("select count(b.id) as count from Booking b where b.createDate between ?1 and ?2")
    Long countBookingOnMonth(LocalDate startDate, LocalDate endDate);

    @Query("select sum(b.deposit) as count from Booking b where b.createDate between ?1 and ?2")
    BigDecimal totalRevenue(LocalDate startDate,LocalDate endDate);


}
