package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository  extends JpaRepository<Status,Long> {
}
