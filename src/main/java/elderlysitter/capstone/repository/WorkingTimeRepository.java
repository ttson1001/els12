package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Long> {
}
