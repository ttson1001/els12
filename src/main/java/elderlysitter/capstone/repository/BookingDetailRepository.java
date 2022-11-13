package elderlysitter.capstone.repository;

import elderlysitter.capstone.dto.ServiceDTO;
import elderlysitter.capstone.entities.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
    List<BookingDetail> findAllByBooking_Id(Long bookingId);

    @Query("select new elderlysitter.capstone.dto.ServiceDTO(s.name ,count(bd.booking.id)) from BookingDetail bd join Service s on s.id = bd.service.id group by s.id")
    List<ServiceDTO> reportService();
}
