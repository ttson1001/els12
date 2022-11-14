package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.SitterCancel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitterCancelRepository extends JpaRepository<SitterCancel,Long> {
}
