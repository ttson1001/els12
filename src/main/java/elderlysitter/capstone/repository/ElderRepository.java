package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Elder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElderRepository extends JpaRepository<Elder, Long> {
}
