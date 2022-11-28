package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Long> {
    List<WorkingTime> findAllByBooking_Id(Long id);
    List<WorkingTime> findAllByBooking_IdAndStatus(Long id,String status);

    List<WorkingTime> findAllByBooking_User_IdAndStatus(Long userId,String status );
}
