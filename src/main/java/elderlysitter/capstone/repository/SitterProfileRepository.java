package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.SitterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitterProfileRepository extends JpaRepository<SitterProfile, Long> {

    SitterProfile findByUser_Email(String email);
}
