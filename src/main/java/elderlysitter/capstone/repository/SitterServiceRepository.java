package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.SitterService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SitterServiceRepository extends JpaRepository<SitterService, Long> {
    List<SitterService> getAllBySitterProfile_User_Email(String email);
}
