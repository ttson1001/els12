package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.SitterProfile;
import elderlysitter.capstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitterProfileRepository extends JpaRepository<SitterProfile, Long> {
}
